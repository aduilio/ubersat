package com.aduilio.viasat.ubersat.api

import com.aduilio.viasat.ubersat.entity.Installer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InstallerApi {

    @GET("installers")
    fun getInstallers(@Query("plan") planId: Long): Call<List<Installer>>
}