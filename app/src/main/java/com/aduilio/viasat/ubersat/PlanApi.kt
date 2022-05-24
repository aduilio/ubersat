package com.aduilio.viasat.ubersat

import com.aduilio.viasat.ubersat.entity.Plan
import retrofit2.Call
import retrofit2.http.GET

interface PlanApi {

    @GET("plans")
    fun getPlans(): Call<List<Plan>>
}