package com.emdasoft.mysavings.data.mapper

import com.emdasoft.mysavings.data.database.CardItemDbModel
import com.emdasoft.mysavings.domain.entity.CardItem
import com.emdasoft.mysavings.domain.entity.Category
import com.emdasoft.mysavings.domain.entity.Currency

class Mapper {

    fun mapDbModelToEntity(dbModel: CardItemDbModel) = CardItem(
        id = dbModel.id,
        title = dbModel.title,
        amount = dbModel.amount,
        type = dbModel.type,
        category = Category.valueOf(dbModel.category),
        currency = Currency.valueOf(dbModel.currency)
    )

    fun mapEntityToDbModel(cardItem: CardItem) = CardItemDbModel(
        id = cardItem.id,
        title = cardItem.title,
        amount = cardItem.amount,
        type = cardItem.type,
        category = cardItem.category.toString(),
        currency = cardItem.currency.toString()
    )

    fun mapDbModelListToEntityList(list: List<CardItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}