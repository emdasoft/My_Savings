package com.emdasoft.mysavings.presentation

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.domain.entity.CardItem

class CardListAdapter(
    private val listener: SetOnClickListeners,
    private val metrics: DisplayMetrics
) :
    RecyclerView.Adapter<CardViewHolder>() {

    private var itemMargin: Int = 0
    private var itemWidth: Int = 0


    var cardsList = listOf<CardItem>()
        set(value) {
            val callback = CardListDiffCallback(cardsList, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
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
        var currentItemWidth = itemWidth
        when (position) {
            0 -> {
                currentItemWidth += itemMargin
                holder.itemView.setPadding(itemMargin, 0, 0, 0)
            }
            itemCount - 1 -> {
                currentItemWidth += itemMargin
                holder.itemView.setPadding(0, 0, itemMargin, 0)
            }
            else -> {
                holder.itemView.setPadding(0, 0, 0, 0)
            }
        }

        val height = holder.itemView.layoutParams.height
        holder.itemView.layoutParams = ViewGroup.LayoutParams(currentItemWidth, height)

        holder.bindItem(cardsList[position], listener)
    }

    fun setItemMargin(itemMargin: Int) {
        this.itemMargin = itemMargin
    }

    fun updateDisplayMetrics() {
        itemWidth = metrics.widthPixels - itemMargin * 2
    }


    interface SetOnClickListeners {

        fun setOnClickListener(cardItem: CardItem)

        fun setOnRecycleClickListener(cardItem: CardItem)

    }

}