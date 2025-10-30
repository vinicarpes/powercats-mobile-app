package com.example.powercats.repositories

import com.example.powercats.dto.RemoteAlert
import com.example.powercats.ui.model.EAlertStatus

interface IAlertRepository<T> {
    suspend fun getData(): List<RemoteAlert?>

    suspend fun updateAlertStatus(
        id: Long,
        status: EAlertStatus,
    )
}
