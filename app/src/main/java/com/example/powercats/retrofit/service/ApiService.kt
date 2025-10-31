package com.example.powercats.retrofit.service

import com.example.powercats.dto.AlertUpdateRequest
import com.example.powercats.dto.RemoteAlert
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ApiService {
    @GET("alerts")
    suspend fun getAlerts(): List<RemoteAlert?>

    @PATCH("alerts/{id}/status")
    suspend fun updateAlertStatus(
        @Path("id") id: Long,
        @Body request: AlertUpdateRequest,
    )
}
