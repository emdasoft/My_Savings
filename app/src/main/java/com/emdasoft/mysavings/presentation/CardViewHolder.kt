package com.emdasoft.mysavings.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.emdasoft.mysavings.databinding.CardItemBinding
import com.emdasoft.mysavings.domain.entity.CardItem

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = CardItemBinding.bind(itemView)

    fun bindItem(cardItem: CardItem, listener: CardListAdapter.SetOnClickListeners) = with(binding) {
        tvCardLabel.text = cardItem.title
        tvCardAmount.text = cardItem.amount.toString()
        tvCategory.text = cardItem.category.toString()
        tvCardCurrency.text = cardItem.currency.toString()
        itemView.setOnClickListener {
            listener.setOnClickListener(cardItem)
        }
        deleteButton.setOnClickListener {
            listener.setOnRecycleClickListener(cardItem)
        }
    }

}