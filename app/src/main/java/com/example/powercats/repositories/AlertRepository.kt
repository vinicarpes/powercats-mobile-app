package com.example.powercats.repositories

import com.example.powercats.dto.RemoteAlert
import com.example.powercats.retrofit.service.ApiService

class AlertRepository(
    private val apiService: ApiService,
) : IAlertRepository<RemoteAlert> {
    override suspend fun getData(): List<RemoteAlert?> {
        var alerts: List<RemoteAlert?> = emptyList()
        runCatching {
            alerts = apiService.getAlerts()
        }
        return alerts
    }
}
