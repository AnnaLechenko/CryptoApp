package com.example.cryptoapp.data.repository

import android.app.Application
import android.service.autofill.Transformation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class ReposytoryImpl(private val application: Application) : CoinRepository{


    val mapperObj = CoinMapper()

    val apiService = ApiFactory.apiService

    private val dao = AppDatabase.getInstance(application)
        .coinPriceInfoDao()



    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
       return Transformations.map(dao.getPriceList()){
           it->
           it.map {
             mapperObj.mapDBModelToEntity(it)
           }
       }
    }

    override fun getCoinInfo(fromSymbal: String): LiveData<CoinInfo> {
        return Transformations.map(dao.getPriceInfoAboutCoin(fromSymbal)){
            it->
            mapperObj.mapDBModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true){
            try {
                val topcoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols= mapperObj.mapNamesListToString(topcoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoList = mapperObj.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoList.map {
                    mapperObj.mapDtoToDmModel(it)
                }
                dao.insertPriceList(dbModelList)
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
            delay(10000)
        }
        }

}