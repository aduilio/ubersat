package com.aduilio.viasat.ubersat.api

import com.aduilio.viasat.ubersat.entity.Plan
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanApi {

    @GET("plans")
    fun getPlans(@Query("state") state: String? = null): Call<List<Plan>>
}