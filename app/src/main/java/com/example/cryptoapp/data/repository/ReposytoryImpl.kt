package com.example.cryptoapp.data.repository

import android.app.Application
import android.service.autofill.Transformation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDbModel
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.model.CoinInfoDto
import com.example.cryptoapp.data.worker.RefrechDataWorker
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

class ReposytoryImpl(private val application: Application) : CoinRepository{


    val mapperObj = CoinMapper()

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

    override  fun loadData() {
        val workManager =  WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefrechDataWorker.SERVICE_NAME,
            ExistingWorkPolicy.REPLACE,
            RefrechDataWorker.makeReqest()

        )
        }

}