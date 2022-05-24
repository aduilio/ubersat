package com.aduilio.viasat.ubersat.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plan(
    var id: Long,
    var isp: String,
    @SerializedName("data_capacity")
    var dataCapacity: Double?,
    @SerializedName("download_speed")
    var downloadSpeed: Double,
    @SerializedName("upload_speed")
    var uploadSpeed: Double,
    var description: String,
    @SerializedName("price_per_month")
    var pricePerMonth: Double,
    @SerializedName("type_of_internet")
    var typeOfInternet: String
) : Parcelable
