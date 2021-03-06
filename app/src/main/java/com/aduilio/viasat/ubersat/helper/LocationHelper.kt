package com.aduilio.viasat.ubersat.helper

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

@SuppressLint("MissingPermission")
class LocationHelper(
    context: Context,
    private var listener: LocationHelperListener?
) {
    private var locationByGps: Location? = null
    private var locationByNetwork: Location? = null
    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByGps = location
            setLocation()
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val networkLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByNetwork = location
            setLocation()
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    init {
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                10F,
                gpsLocationListener
            )
        }


        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                10F,
                networkLocationListener
            )
        }

        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }

        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }

        setLocation()
    }

    private fun setLocation() {
        if (locationByGps != null && locationByNetwork != null) {
            val currentLocation = if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {
                locationByGps
            } else {
                locationByNetwork
            }

            listener?.locationChange(currentLocation!!)
        } else if (locationByNetwork != null) {
            listener?.locationChange(locationByNetwork!!)
        } else if (locationByGps != null) {
            listener?.locationChange(locationByGps!!)
        }

        listener = null
    }

    interface LocationHelperListener {
        fun locationChange(location: Location)
    }
}