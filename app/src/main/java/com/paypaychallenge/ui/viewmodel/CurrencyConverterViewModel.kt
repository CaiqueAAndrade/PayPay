package com.paypaychallenge.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paypaychallenge.R
import com.paypaychallenge.data.remote.Result
import com.paypaychallenge.model.Quote
import com.paypaychallenge.repository.CurrencyConverterRepository
import com.paypaychallenge.data.remote.Event
import com.paypaychallenge.util.Utils
import kotlinx.coroutines.launch

class CurrencyConverterViewModel(
    mApplication: Application,
    private val repository: CurrencyConverterRepository
) : AndroidViewModel(mApplication) {

    private var quotesToMap: Map<String, Double> = mutableMapOf()
    private var quotesToArray: List<Quote> = arrayListOf()

    private val _errorMutableLiveData = MutableLiveData<Event<String>>()
    val errorLiveData: LiveData<Event<String>>
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
                        repository.insert(liveCurrencyLiveData)
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

                        quotesToMap = liveCurrencyLiveData.quotes
                        quotesToArray = quotes

                        _quotesMutableLiveData.value = Event(quotes)
                        _currenciesMutableLiveData.value = Event(currencies)
                    }
                }
                is Result.Failure -> {
                    val errorMessage =
                        getApplication<Application>().resources.getString(R.string.network_error_message)
                    _errorMutableLiveData.value =
                        Event(errorMessage.replace("%a", response.statusCode.toString()))
                }
                is Result.NetworkError -> {
                    val errorMessage =
                        getApplication<Application>().resources.getString(R.string.network_error_message)
                    val internetError =
                        getApplication<Application>().resources.getString(R.string.internet_error_description)
                    _errorMutableLiveData.value = Event(errorMessage.replace("%a", internetError))
                }
            }
        }
    }

    fun getExchangedValueFromCurrency(currencyCode: String, selectedValue: Double) {
        val convertedQuotes =
            Utils.getExchangedQuotesList(currencyCode, quotesToMap, quotesToArray, selectedValue)
        _quotesMutableLiveData.value = Event(convertedQuotes)
    }

}