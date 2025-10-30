package com.example.powercats.usecases

import com.example.powercats.dto.toAlertUi
import com.example.powercats.repositories.AlertRepository
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.model.EAlertStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlertUseCase(
    private val repository: AlertRepository,
) {
    suspend fun getAlerts(): Result<List<AlertUi>> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository
                    .getData()
                    .mapNotNull { it?.toAlertUi() }
            }
        }

    suspend fun updateAlertStatus(
        id: Long,
        status: EAlertStatus,
    ): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                repository.updateAlertStatus(id, status)
            }
        }
}
