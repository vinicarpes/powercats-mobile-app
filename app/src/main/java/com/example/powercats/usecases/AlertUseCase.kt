package com.example.powercats.usecases

import com.example.powercats.dto.toAlertUi
import com.example.powercats.repositories.AlertRepository
import com.example.powercats.ui.model.AlertUi
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
}
