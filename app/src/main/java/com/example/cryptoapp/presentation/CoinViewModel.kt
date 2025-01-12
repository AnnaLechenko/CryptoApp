package com.example.cryptoapp.presentation

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.repository.ReposytoryImpl
import com.example.cryptoapp.domain.GatCoinInfoUseCase
import com.example.cryptoapp.domain.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ReposytoryImpl(application)

    val getCoinInfoUseCase  = GatCoinInfoUseCase(repository)
    val getCoinInfoLidtUseCase =GetCoinInfoListUseCase(repository)
    val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoLidtUseCase()

    fun getDetailInfo(fSym: String)= getCoinInfoUseCase(fSym)



    init {
           loadDataUseCase()
    }



}