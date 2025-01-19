package com.example.cryptoapp.domain

import javax.inject.Inject

class LoadDataUseCase @Inject constructor(val repository: CoinRepository) {
    operator  fun invoke(){
        repository.loadData()
    }


}