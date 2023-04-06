package com.emdasoft.mysavings.data.mapper

import com.emdasoft.mysavings.data.database.CardItemDbModel
import com.emdasoft.mysavings.domain.entity.CardItem

class Mapper {

    fun mapDbModelToEntity(dbModel: CardItemDbModel) = CardItem(
        id = dbModel.id,
        title = dbModel.title,
        amount = dbModel.amount,
        type = dbModel.type,
        category = dbModel.category
    )

    fun mapEntityToDbModel(cardItem: CardItem) = CardItemDbModel(
        id = cardItem.id,
        title = cardItem.title,
        amount = cardItem.amount,
        type = cardItem.type,
        category = cardItem.category
    )

    fun mapDbModelListToEntityList(list: List<CardItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}