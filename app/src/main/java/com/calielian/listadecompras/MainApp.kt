package com.calielian.listadecompras

import android.app.Application
import com.calielian.listadecompras.database.ListaDatabase
import com.google.android.material.color.DynamicColors

class MainApp : Application() {

    val database: ListaDatabase by lazy { ListaDatabase.pegarDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}