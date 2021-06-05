package com.jumpywiz.tinkofftest

import android.app.Application
import com.jumpywiz.tinkofftest.db.GifDao
import com.jumpywiz.tinkofftest.db.MainDatabase
import com.jumpywiz.tinkofftest.di.*
import kotlinx.coroutines.*
import javax.inject.Inject

class Application : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        CoroutineScope(Dispatchers.IO).launch {
            MainDatabase.getInstance(applicationContext).clearAllTables()
        }
    }

    fun initDagger() {
        appComponent =
            DaggerAppComponent.builder().appModule(AppModule(context = this.applicationContext))
                .databaseModule(
                    DatabaseModule()
                ).remoteModule(RemoteModule()).reposModule(ReposModule())
                .build()
    }

}