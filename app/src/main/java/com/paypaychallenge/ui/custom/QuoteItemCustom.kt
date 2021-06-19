package com.paypaychallenge.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.paypaychallenge.R
import com.paypaychallenge.extensions.roundUpToCurrency
import com.paypaychallenge.model.Quote

class QuoteItemCustom @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var background: MaterialCardView
    private var currencyCode: TextView
    private var currencyValue: TextView
    private var valueStatusIcon: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_quote, this, true)
        background = findViewById(R.id.mcv_quote_card)
        currencyCode = findViewById(R.id.tv_currency_code)
        currencyValue = findViewById(R.id.tv_currency_value)
        valueStatusIcon = findViewById(R.id.iv_value_status_icon)
    }

    fun setupLayout(quote: Quote, selectedValue: Double) {
        currencyCode.text = quote.currencyCode
        currencyValue.text = quote.currencyValue.roundUpToCurrency(quote.currencyCode)

        when {
            quote.currencyValue < selectedValue -> {
                background.strokeColor = ContextCompat.getColor(context, android.R.color.holo_red_dark)
                valueStatusIcon.setImageResource(R.drawable.ic_arrow_down)
            }
            quote.currencyValue == selectedValue -> {
                background.strokeColor = ContextCompat.getColor(context, R.color.gray_5C5C5C)
                valueStatusIcon.setImageResource(R.drawable.ic_minus)
            }
            else -> {
                background.strokeColor = ContextCompat.getColor(context, android.R.color.holo_green_dark)
                valueStatusIcon.setImageResource(R.drawable.ic_arrow_up)
            }
        }
    }
}