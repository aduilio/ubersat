package com.aduilio.viasat.ubersat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aduilio.viasat.ubersat.PlanApi
import com.aduilio.viasat.ubersat.entity.Plan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlanViewModel : ViewModel() {

    object Constants {
        const val SERVER_URL = "https://app-challenge-api.herokuapp.com/"
    }

    private lateinit var plansApi: PlanApi

    init {
        setupHttpClient()
    }

    val plans: MutableLiveData<List<Plan>> by lazy {
        MutableLiveData<List<Plan>>()
    }

    val showProgress: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val resultSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun get() {
        showProgress.value = true

        plansApi.getPlans().enqueue(object : Callback<List<Plan>> {
            override fun onResponse(call: Call<List<Plan>>, response: Response<List<Plan>>) {
                showProgress.value = false
                setResultSuccess(response.isSuccessful)

                handleResponse(response)
            }

            override fun onFailure(call: Call<List<Plan>>, t: Throwable) {
                showProgress.value = false
                setResultSuccess(false)
            }
        })
    }

    private fun setupHttpClient() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        plansApi = retrofit.create(PlanApi::class.java)
    }

    private fun handleResponse(response: Response<List<Plan>>) {
        if (response.isSuccessful) {
            response.body()?.let {
                plans.value = it
            }
        }
    }

    private fun setResultSuccess(success: Boolean) {
        resultSuccess.value = success
    }
}