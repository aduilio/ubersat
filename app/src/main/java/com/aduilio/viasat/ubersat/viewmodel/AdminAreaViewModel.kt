package com.aduilio.viasat.ubersat.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aduilio.viasat.ubersat.entity.AdminArea
import java.util.*

class AdminAreaViewModel() : ViewModel() {

    val adminAreaCode: MutableLiveData<AdminArea> by lazy {
        MutableLiveData<AdminArea>()
    }

//    fun get(state: String? = null) {
//        showProgress.value = true
//
//        plansApi.getPlans().enqueue(object : Callback<List<Plan>> {
//            override fun onResponse(call: Call<List<Plan>>, response: Response<List<Plan>>) {
//                showProgress.value = false
//                setResultSuccess(response.isSuccessful)
//
//                handleResponse(response)
//            }
//
//            override fun onFailure(call: Call<List<Plan>>, t: Throwable) {
//                showProgress.value = false
//                setResultSuccess(false)
//            }
//        })
//    }

    fun get(location: Location, context: Context) {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> =
            geocoder.getFromLocation(location.latitude, location.longitude, 1)

        getAdminAreaCode(addresses[0].adminArea)?.let {
            adminAreaCode.value = AdminArea(addresses[0].adminArea, it)
        } ?: run {
            adminAreaCode.value = null
        }
    }

    companion object {
        fun getAdminAreaCode(adminArea: String): String? {
            return when (adminArea) {
                "Acre" -> "AC"
                "Alagoas" -> "AL"
                "Amapá" -> "AP"
                "Amazonas" -> "AM"
                "Bahia" -> "BA"
                "Ceará" -> "CE"
                "Distrito Federal" -> "DF"
                "Espírito Santo" -> "ES"
                "Goiás" -> "GO"
                "Maranhão" -> "MA"
                "Mato Grosso" -> "MT"
                "Mato Grosso do Sul" -> "MS"
                "Minas Gerais" -> "MG"
                "Pará" -> "PA"
                "Paraíba" -> "PB"
                "Paraná" -> "PR"
                "Pernambuco" -> "PE"
                "Piauí" -> "PI"
                "Rio de Janeiro" -> "RJ"
                "Rio Grande do Norte" -> "RN"
                "Rio Grande do Sul" -> "RS"
                "Rondônia" -> "RO"
                "Roraima" -> "RR"
                "Santa Catarina" -> "SC"
                "São Paulo" -> "SP"
                "Sergipe" -> "SE"
                "Tocantins" -> "TO"
                else -> null
            }
        }
    }
}