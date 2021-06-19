package com.paypaychallenge.ui

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import com.paypaychallenge.R
import com.paypaychallenge.databinding.ActivityCurrencyConverterBinding
import com.paypaychallenge.ui.viewmodel.CurrencyConverterViewModel
import com.paypaychallenge.util.EventObserver
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CurrencyConverterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCurrencyConverterBinding

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

        viewModel.getLiveCurrency()
        subscribe()
    }

    private fun subscribe() {
        viewModel.liveCurrencyLiveData.observe(this, EventObserver {
            binding.tvCurrency.text = it.timestamp.toString()
        })

        viewModel.errorLiveData.observe(this, EventObserver {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show()
        })
    }
}