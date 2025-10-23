package com.example.powercats.repositories

import com.example.powercats.dto.RemoteAlert

interface IAlertRepository<T> {
    suspend fun buscarDados(): List<RemoteAlert?>
}
