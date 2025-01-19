package com.example.cryptoapp.di

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.cryptoapp.presentation.CoinDetailFragment
import com.example.cryptoapp.presentation.CoinPriceListActivity
import com.example.cryptoapp.presentation.CoinViewModel
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {



    fun  inject(activity: CoinPriceListActivity)
    fun intject(fragment: CoinDetailFragment)

    @Component.Factory
    interface  Factory{

        fun create(@BindsInstance application: Application) : ApplicationComponent
    }
}