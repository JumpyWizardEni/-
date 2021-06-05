package com.jumpywiz.tinkofftest.di

import com.jumpywiz.tinkofftest.presentation.ui.MainActivity
import com.jumpywiz.tinkofftest.presentation.viewmodels.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = arrayOf(
        AppModule::class,
        DatabaseModule::class,
        RemoteModule::class,
        ReposModule::class,
        ViewModelModule::class
    )
)
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(viewModel: MainViewModel)
}