package com.aduilio.viasat.ubersat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aduilio.viasat.ubersat.api.InstallerApi
import com.aduilio.viasat.ubersat.common.Constants
import com.aduilio.viasat.ubersat.entity.Installer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InstallerViewModel : ViewModel() {

    private lateinit var installerApi: InstallerApi

    init {
        setupHttpClient()
    }

    val installers: MutableLiveData<List<Installer>> by lazy {
        MutableLiveData<List<Installer>>()
    }

    val showProgress: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val resultSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun get(planId: Long) {
        showProgress.value = true

        installerApi.getInstallers(planId).enqueue(object : Callback<List<Installer>> {
            override fun onResponse(
                call: Call<List<Installer>>,
                response: Response<List<Installer>>
            ) {
                showProgress.value = false
                setResultSuccess(response.isSuccessful)

                handleResponse(response)
            }

            override fun onFailure(call: Call<List<Installer>>, t: Throwable) {
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

        installerApi = retrofit.create(InstallerApi::class.java)
    }

    private fun handleResponse(response: Response<List<Installer>>) {
        if (response.isSuccessful) {
            response.body()?.let {
                installers.value = it
            }
        }
    }

    private fun setResultSuccess(success: Boolean) {
        resultSuccess.value = success
    }
}