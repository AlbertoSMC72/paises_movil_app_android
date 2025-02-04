package com.example.state.paises.data.datasource

import com.example.state.paises.data.model.Country
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CountryService {

    @GET("app/paises")
    suspend fun getCountries(): Response<List<Country>>

    @POST("app/paises")
    suspend fun addCountry(@Body country: Country): Response<Void>
}
