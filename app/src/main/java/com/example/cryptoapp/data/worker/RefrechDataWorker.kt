package com.example.cryptoapp.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.presentation.CoinApp
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefrechDataWorker(
    context: Context,
    workerParameters: WorkerParameters,
   private  val mapperObj :CoinMapper,
   private  val apiService:ApiService,
    private  val dao : CoinInfoDao
): CoroutineWorker(context,workerParameters){




    override suspend fun doWork(): Result {
       while (true) {
           try {
               val topcoins = apiService.getTopCoinsInfo(limit = 50)
               val fromSymbols = mapperObj.mapNamesListToString(topcoins)
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


    //мини фабрика для каждого класса - реализация
    class Factory @Inject constructor(
        private  val mapperObj :CoinMapper,
        private  val apiService:ApiService,
        private  val dao : CoinInfoDao
    ):ChildWorkerFactory{
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefrechDataWorker(
                context,workerParameters,mapperObj,apiService,dao
            )
        }
    }


    companion object{
        const val SERVICE_NAME = "RefreshDataWorker"

        fun makeReqest():OneTimeWorkRequest{
            return OneTimeWorkRequestBuilder<RefrechDataWorker>().build()
        }
    }
}