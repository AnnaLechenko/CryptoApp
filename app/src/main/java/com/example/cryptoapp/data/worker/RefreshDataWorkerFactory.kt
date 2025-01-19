package com.example.cryptoapp.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.ApiService
import javax.inject.Inject

class RefreshDataWorkerFactory @Inject constructor(
    private  val mapperObj : CoinMapper,
    private  val apiService: ApiService,
    private  val dao : CoinInfoDao
) : WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefrechDataWorker(
            appContext,
            workerParameters,
            mapperObj,
            apiService,
            dao
        )
    }


}