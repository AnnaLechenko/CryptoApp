package com.example.cryptoapp.domain

import androidx.lifecycle.LiveData

class GatCoinInfoUseCase(private val repository: CoinRepository) {
    operator fun invoke(fromSymbol:String):LiveData<CoinInfo>{
        return repository.getCoinInfo(fromSymbol)
    }
}