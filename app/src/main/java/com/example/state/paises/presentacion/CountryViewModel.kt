package com.example.state.paises.presentacion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.state.paises.data.model.Country
import com.example.state.paises.data.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {
    private val repository = CountryRepository()

    private val _countries = MutableStateFlow<List<Country>>(emptyList())
    val countries: StateFlow<List<Country>> = _countries.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _showModal = MutableStateFlow(false)
    val showModal: StateFlow<Boolean> = _showModal.asStateFlow()

    private val _result = MutableStateFlow<Result<List<Country>>?>(null)
    val result: StateFlow<Result<List<Country>>?> = _result.asStateFlow()

    init {
        if (_countries.value.isEmpty()) {
            loadCountries()
        }
    }

    fun loadCountries() {
        viewModelScope.launch {
            if (_countries.value.isNotEmpty()) return@launch
            _isLoading.value = true
            val fetchResult = repository.getCountries()
            _result.value = fetchResult
            fetchResult.onSuccess {
                if (it.isNotEmpty()) {
                    _countries.value = it
                }
                _errorMessage.value = null
            }.onFailure {
                _errorMessage.value = it.message
            }
            _isLoading.value = false
        }
    }

    fun toggleModal() {
        _showModal.value = !_showModal.value
    }

    fun addCountry(country: Country) {
        viewModelScope.launch {
            _isLoading.value = true
            val addResult = repository.addCountry(country)
            addResult.onSuccess {
                _showModal.value = false
                loadCountries()
            }.onFailure {
                _errorMessage.value = it.message
            }
            _isLoading.value = false
        }
    }
}