package com.paypaychallenge.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paypaychallenge.model.LiveCurrencyResponse
import com.paypaychallenge.repository.CurrencyConverterRepository
import com.paypaychallenge.util.Event
import kotlinx.coroutines.launch
import com.paypaychallenge.data.remote.Result

class CurrencyConverterViewModel(
    mApplication: Application,
    private val repository: CurrencyConverterRepository
) : AndroidViewModel(mApplication) {


    private val _liveCurrencyMutableLiveData = MutableLiveData<Event<LiveCurrencyResponse>>()
    val liveCurrencyLiveData: LiveData<Event<LiveCurrencyResponse>>
        get() = _liveCurrencyMutableLiveData

    private val _errorMutableLiveData = MutableLiveData<Event<Unit>>()
    val errorLiveData: LiveData<Event<Unit>>
        get() = _errorMutableLiveData


    fun getLiveCurrency() {
        viewModelScope.launch {
            when (val response = repository.getLiveCurrency()) {
                is Result.Success -> {
                    response.data?.let {
                        _liveCurrencyMutableLiveData.value = Event(it)
                    }
                }
                is Result.Failure -> {
                    _errorMutableLiveData.value = Event(Unit)
                }
                is Result.NetworkError -> {
                    _errorMutableLiveData.value = Event(Unit)
                }
            }
        }
    }
}