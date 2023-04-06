package com.emdasoft.mysavings.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.CardItemBinding
import com.emdasoft.mysavings.domain.entity.CardItem

class CardListAdapter : RecyclerView.Adapter<CardListAdapter.CardViewHolder>() {

    var cardsList = listOf<CardItem>()
        set(value) {
            val callback = CardListDiffCallback(cardsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardItemBinding.bind(itemView)
        fun bindItem(cardItem: CardItem) = with(binding) {
            tvCardLabel.text = cardItem.title
            tvCardAmount.text = cardItem.amount.toString()
            tvCategory.text = cardItem.category
            tvCardCurrency.text = cardItem.currency
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_item,
            parent,
            false
        )
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardsList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bindItem(cardsList[position])
    }
}