package com.example.cryptoapp.di

import com.example.cryptoapp.data.worker.ChildWorkerFactory
import com.example.cryptoapp.data.worker.RefrechDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefrechDataWorker::class)
    fun bindsRefreshDataWorker(worker: RefrechDataWorker.Factory): ChildWorkerFactory

}