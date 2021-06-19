package com.paypaychallenge.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.paypaychallenge.R
import com.paypaychallenge.databinding.ActivityCurrencyConverterBinding
import com.paypaychallenge.model.Quote
import com.paypaychallenge.ui.viewmodel.CurrencyConverterViewModel
import com.paypaychallenge.util.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class CurrencyConverterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    QuotesRecyclerViewAdapter.QuoteItemClickListener {

    private lateinit var binding: ActivityCurrencyConverterBinding

    private val adapter = QuotesRecyclerViewAdapter(this, this)
    private val viewModel by viewModel<CurrencyConverterViewModel> {
        parametersOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_converter)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val wic = WindowInsetsControllerCompat(window, window.decorView)
            wic.isAppearanceLightStatusBars = true
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        }

        binding.rvExchangedCurrencyList.layoutManager = GridLayoutManager(this, 3)
        viewModel.getLiveCurrency()
        subscribe()
    }

    private fun subscribe() {
        viewModel.liveCurrencyLiveData.observe(this, EventObserver {
//            binding.tvCurrency.text = it.timestamp.toString()
        })

        viewModel.currenciesLiveData.observe(this, EventObserver {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this@CurrencyConverterActivity,
                android.R.layout.simple_spinner_item, it
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spCurrencySelection.adapter = adapter
            binding.spCurrencySelection.onItemSelectedListener = this
        })

        viewModel.quoteMutableLiveData.observe(this, EventObserver {
            adapter.setData(it)
            binding.rvExchangedCurrencyList.adapter = adapter
        })

        viewModel.errorLiveData.observe(this, EventObserver {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Toast.makeText(this, p0?.getItemAtPosition(p2).toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun quotesItemClickListener(quote: Quote) {
        Toast.makeText(this, quote.currencyCode, Toast.LENGTH_SHORT).show()
    }
}