package com.paypaychallenge.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.paypaychallenge.R
import com.paypaychallenge.databinding.ItemQuoteBinding
import com.paypaychallenge.model.Quote

class QuotesRecyclerViewAdapter(
    val listener: QuoteItemClickListener
) : RecyclerView.Adapter<QuotesRecyclerViewAdapter.QuotesViewHolder>() {

    private var items: List<Quote> = arrayListOf()
    private var selectedValue: Double = 0.0

    interface QuoteItemClickListener {
        fun quotesItemClickListener(quote: Quote)
    }

    fun setData(quotes: List<Quote>, selectedValue: Double) {
        this.items = quotes
        this.selectedValue = selectedValue
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
                qicQuoteCard.setupLayout(quote, selectedValue)
            }
        }

        override fun onClick(p0: View?) {
            listener.quotesItemClickListener(items[adapterPosition])
        }

    }
}