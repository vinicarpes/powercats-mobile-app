package com.example.powercats.ui.model

import java.io.Serializable
import kotlinx.serialization.Serializable as KxSerializable

@KxSerializable
data class AlertUi(
    val location: String,
    val latitude: String,
    val longitude: String,
    val dateTime: String,
    val alertLevel: String,
    val status: String,
    val description: String,
) : Serializable
