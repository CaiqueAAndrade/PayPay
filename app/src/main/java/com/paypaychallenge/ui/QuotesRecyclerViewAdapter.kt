package com.paypaychallenge.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paypaychallenge.R
import com.paypaychallenge.databinding.ItemQuoteBinding
import com.paypaychallenge.extensions.roundUpToCurrency
import com.paypaychallenge.model.Quote

class QuotesRecyclerViewAdapter(
    val context: Context,
    val listener: QuoteItemClickListener
) : RecyclerView.Adapter<QuotesRecyclerViewAdapter.QuotesViewHolder>() {

    private var items: List<Quote> = arrayListOf()

    interface QuoteItemClickListener {
        fun quotesItemClickListener(quote: Quote)
    }

    fun setData(quotes: List<Quote>) {
        this.items = quotes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesRecyclerViewAdapter.QuotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemQuoteBinding>(
            inflater,
            R.layout.item_quote,
            parent,
            false
        )
        parent.isMotionEventSplittingEnabled = false
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: QuotesRecyclerViewAdapter.QuotesViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class QuotesViewHolder(private val binding: ItemQuoteBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(quote: Quote) {
            binding.apply {
                tvCurrencyCode.text = quote.currencyCode
                tvCurrencyValue.text = quote.currencyValue.roundUpToCurrency(quote.currencyCode)
            }
        }

        override fun onClick(p0: View?) {
            listener.quotesItemClickListener(items[adapterPosition])
        }

    }
}