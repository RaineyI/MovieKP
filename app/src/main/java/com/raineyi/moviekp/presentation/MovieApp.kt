package com.raineyi.moviekp.presentation

import android.app.Application
import com.raineyi.moviekp.di.DaggerApplicationComponent

class MovieApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}