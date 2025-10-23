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
        alertLevel = alertLevel,
        status = statusAlert,
    )
