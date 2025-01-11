package com.example.cryptoapp.domain

class LoadDataUseCase(val repository: CoinRepository) {
    operator suspend fun invoke(){
        repository.loadData()
    }


}