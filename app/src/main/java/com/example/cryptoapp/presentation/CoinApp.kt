package com.example.cryptoapp.presentation

import android.app.Application
import com.example.cryptoapp.di.DaggerApplicationComponent

class CoinApp:Application() {
    val componentContext by lazy {
            DaggerApplicationComponent.factory().create(application = this)
    }

}