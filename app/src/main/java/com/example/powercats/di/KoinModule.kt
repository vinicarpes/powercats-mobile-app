package com.example.powercats.di

import AlertsViewModel
import com.example.listaelementos.retrofit.provideOkHttpClient
import com.example.listaelementos.retrofit.provideRetrofit
import com.example.powercats.repositories.AlertRepository
import com.example.powercats.retrofit.service.ApiService
import com.example.powercats.usecases.AlertUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule =
    module {
        viewModel { AlertsViewModel(get()) }
    }

val networkModule =
    module {
        single { provideOkHttpClient(true) }
        single { provideRetrofit(get()) }
        single<ApiService> { get<Retrofit>().create(ApiService::class.java) }
        single { AlertRepository(get()) }
        single { AlertUseCase(get()) }
    }
