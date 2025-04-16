package com.example.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.worker.RefreshDataWorkerFactory
import com.example.cryptoapp.di.DaggerApplicationComponent


import javax.inject.Inject

class CoinApp : Application(), Configuration.Provider {


    @Inject
    lateinit var workerFactory: RefreshDataWorkerFactory


    val componentContext by lazy {
        DaggerApplicationComponent.factory().create(application = this)
    }

    override fun onCreate() {
        super.onCreate()
        componentContext.inject(this)
    }


    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}


