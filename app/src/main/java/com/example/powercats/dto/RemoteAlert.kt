package com.example.powercats.dto

import com.example.powercats.ui.model.AlertUi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class RemoteAlert(
    val id: Long?,
    val alertDate: String,
    val alertLevel: String,
    val statusAlert: String,
    val description: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)

fun RemoteAlert.toAlertUi(): AlertUi =
    AlertUi(
        location = "",
        latitude = latitude,
        longitude = longitude,
        dateTime = mapAlertDate(alertDate),
        alertLevel = mapAlertLevel(alertLevel),
        status = mapStatusAlert(statusAlert),
        description = description,
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

private fun mapAlertDate(alertDate: String): String {
    val zoned = ZonedDateTime.parse(alertDate)
    return zoned
        .withZoneSameInstant(ZoneId.of("America/Sao_Paulo"))
        .format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
}
