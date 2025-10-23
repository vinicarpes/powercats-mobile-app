package com.example.powercats.dto

import com.example.powercats.ui.model.AlertUi

data class RemoteAlert(
    val id: Long?,
    val alertDate: String,
    val alertLevel: String,
    val statusAlert: String,
    val description: String,
)

fun RemoteAlert.toAlertUi(): AlertUi =
    AlertUi(
        location = description,
        latitude = "",
        longitude = "",
        dateTime = alertDate,
        alertLevel = mapAlertLevel(alertLevel),
        status = mapStatusAlert(statusAlert),
    )

private fun mapAlertLevel(level: String): String =
    when (level.uppercase()) {
        "LOW" -> "Baixo"
        "MODERATE" -> "Médio"
        "SEVERE" -> "Alto"
        "CRITICAL" -> "Crítico"
        else -> level
    }

private fun mapStatusAlert(status: String): String =
    when (status.uppercase()) {
        "STARTED" -> "Ativo"
        "PENDING" -> "Pendente"
        "CANCELLED" -> "Cancelado"
        "FULFILLED" -> "Resolvido"
        else -> status
    }
