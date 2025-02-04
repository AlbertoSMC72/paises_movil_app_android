package com.example.state.paises.data.repository

import com.example.state.core.network.RetrofitHelper
import com.example.state.paises.data.datasource.CountryService
import com.example.state.paises.data.model.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepository {
    private val service: CountryService = RetrofitHelper.countryService

    suspend fun getCountries(): Result<List<Country>> = withContext(Dispatchers.IO) {
        try {
            val response = service.getCountries()
            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()
                Result.success(body)
            } else {
                Result.failure(Exception("Error al cargar los países"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addCountry(country: Country): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = service.addCountry(country)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al agregar el país"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
