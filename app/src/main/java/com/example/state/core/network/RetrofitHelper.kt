package com.example.state.core.network

import com.example.state.register.data.datasource.RegisterService
import com.example.state.paises.data.datasource.CountryService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "http://54.144.10.234:3000"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

    val registerService: RegisterService by lazy {
        retrofit.create(RegisterService::class.java)
    }

    val countryService: CountryService by lazy {
        retrofit.create(CountryService::class.java)
    }
}
