package com.example.cryptoapp.domain

class LoadDataUseCase(val repository: CoinRepository) {
    operator  fun invoke(){
        repository.loadData()
    }


}