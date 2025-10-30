package com.example.powercats.ui.model

import java.io.Serializable
import kotlinx.serialization.Serializable as KxSerializable

@KxSerializable
data class AlertUi(
    val id: Long = 0,
    val location: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val dateTime: String = "",
    val alertLevel: String = "",
    var status: String = "",
    val description: String = "",
) : Serializable
