package com.example.powercats.repositories

import android.util.Log
import com.example.powercats.dto.AlertUpdateRequest
import com.example.powercats.dto.RemoteAlert
import com.example.powercats.retrofit.service.ApiService
import com.example.powercats.ui.model.EAlertStatus

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

    override suspend fun updateAlertStatus(
        id: Long,
        status: EAlertStatus,
    ) {
        Log.d("AlertStatusUpdate", "updateAlertStatus: $id, $status")
        runCatching {
            apiService.updateAlertStatus(id = id, request = AlertUpdateRequest(status = status))
        }
    }
}
