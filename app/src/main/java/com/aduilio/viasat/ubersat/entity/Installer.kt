package com.aduilio.viasat.ubersat.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Installer(
    var id: Long,
    var name: String,
    var rating: Int,
    @SerializedName("price_per_km")
    var pricePerKm: Double,
    var lat: Double,
    var lng: Double
) : Parcelable