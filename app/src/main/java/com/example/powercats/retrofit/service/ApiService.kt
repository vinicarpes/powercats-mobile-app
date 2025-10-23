package com.example.powercats.retrofit.service

import com.example.powercats.dto.RemoteAlert
import retrofit2.http.GET

interface ApiService {
    @GET("alerts")
    suspend fun getAlerts(): List<RemoteAlert?>
}
