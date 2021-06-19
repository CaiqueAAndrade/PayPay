package com.paypaychallenge.ui

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.paypaychallenge.extensions.currencyToDouble
import com.paypaychallenge.extensions.toNumericString
import com.paypaychallenge.model.Quote
import com.paypaychallenge.ui.viewmodel.CurrencyConverterViewModel
import com.paypaychallenge.util.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.NumberFormat
import java.util.*


class CurrencyConverterActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    QuotesRecyclerViewAdapter.QuoteItemClickListener {

    private lateinit var binding: ActivityCurrencyConverterBinding

    private val adapter = QuotesRecyclerViewAdapter(this)
    private val viewModel by viewModel<CurrencyConverterViewModel> {
        parametersOf()
    }
    private var currentCurrencyCode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_currency_converter)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val wic = WindowInsetsControllerCompat(window, window.decorView)
            wic.isAppearanceLightStatusBars = true
            window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
        }

        setupView()
        viewModel.getLiveCurrency()
        subscribe()
    }

    private fun setupView() {
        binding.apply {
            rvExchangedCurrencyList.layoutManager =
                GridLayoutManager(this@CurrencyConverterActivity, 2)

            etInputValueToExchange.addTextChangedListener(
                object : TextWatcher {
                    var current = ""
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun afterTextChanged(s: Editable?) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        p0?.let { s ->
                            if (s.toString() != current) {
                                etInputValueToExchange.removeTextChangedListener(this)

                                val parsed = s.toString().toNumericString().toDouble()
                                val formatted =
                                    NumberFormat.getCurrencyInstance().format((parsed / 100))
                                current = formatted
                                etInputValueToExchange.setText(formatted)
                                etInputValueToExchange.setSelection(formatted.length)
                                etInputValueToExchange.addTextChangedListener(this)

                                viewModel.getExchangedValueFromCurrency(
                                    currentCurrencyCode,
                                    formatted.toString().currencyToDouble()
                                )
                            }
                        }
                    }
                }
            )
        }
    }

    private fun subscribe() {

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
            adapter.setData(it, getSelectedValue())
            binding.rvExchangedCurrencyList.adapter = adapter
        })

        viewModel.errorLiveData.observe(this, EventObserver {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        currentCurrencyCode = p0?.getItemAtPosition(p2).toString()
        viewModel.getExchangedValueFromCurrency(currentCurrencyCode, getSelectedValue())
    }

    private fun getSelectedValue(): Double {
        return if (binding.etInputValueToExchange.text.toString() != "") {
            binding.etInputValueToExchange.text.toString().currencyToDouble()
        } else {
            1.0
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun quotesItemClickListener(quote: Quote) {
        Toast.makeText(this, quote.currencyCode, Toast.LENGTH_SHORT).show()
    }
}