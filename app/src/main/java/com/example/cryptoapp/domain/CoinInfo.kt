package com.example.cryptoapp.domain

import androidx.room.PrimaryKey
import com.example.cryptoapp.R


data class CoinInfo(
    val price: String?,
    val lowDay: String?,
    val highDay: String?,
    val lastMarket: String?,
    val lastUpdate: Long?,

    val fromSymbol: String,
    val toSymbol: String?,
    val imageUrl: String?

)




