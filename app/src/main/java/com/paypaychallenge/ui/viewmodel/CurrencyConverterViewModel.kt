package com.paypaychallenge.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paypaychallenge.data.remote.Result
import com.paypaychallenge.model.LiveCurrencyResponse
import com.paypaychallenge.model.Quote
import com.paypaychallenge.repository.CurrencyConverterRepository
import com.paypaychallenge.util.Event
import kotlinx.coroutines.launch

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

    private val _currenciesMutableLiveData = MutableLiveData<Event<List<String>>>()
    val currenciesLiveData: LiveData<Event<List<String>>>
        get() = _currenciesMutableLiveData

    private val _quotesMutableLiveData = MutableLiveData<Event<List<Quote>>>()
    val quoteMutableLiveData: LiveData<Event<List<Quote>>>
        get() = _quotesMutableLiveData


    fun getLiveCurrency() {
        viewModelScope.launch {
            when (val response = repository.getLiveCurrency()) {
                is Result.Success -> {
                    response.data?.let { liveCurrencyLiveData ->

                        val currencies = arrayListOf<String>()
                        currencies.add("USD")

                        val quotes = arrayListOf<Quote>()
                        quotes.add(Quote("USD", 1.0))

                        liveCurrencyLiveData.quotes.forEach {
                            if (it.key != "USDUSD") {
                                val currencyCode = it.key.replaceFirst("USD", "")
                                currencies.add(currencyCode)
                                quotes.add(Quote(currencyCode, it.value))
                            }
                        }

                        _quotesMutableLiveData.value = Event(quotes)
                        _currenciesMutableLiveData.value = Event(currencies)
                        _liveCurrencyMutableLiveData.value = Event(liveCurrencyLiveData)
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