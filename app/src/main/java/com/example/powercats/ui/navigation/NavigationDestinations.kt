package com.example.powercats.ui.navigation

import android.content.Context
import android.content.Intent
import com.example.powercats.ui.activities.AlertListingComposeActivity

sealed class Destination(
    val title: String,
) {
    data object Alerts : Destination("Notificações e Alertas")

    data object Sensors : Destination("Cadastro de Sensores")

    data object Map : Destination("Mapa Geolocalização")

    data object Users : Destination("Cadastro de Usuários")

    data object Reports : Destination("Relatório de Alertas")
}

fun navigateTo(
    context: Context,
    destination: Destination,
) {
    val intent =
        when (destination) {
            Destination.Alerts -> Intent(context, AlertListingComposeActivity::class.java)
            Destination.Sensors -> TODO("Ainda não implementado")
            Destination.Map -> TODO("Ainda não implementado")
            Destination.Users -> TODO("Ainda não implementado")
            Destination.Reports -> TODO("Ainda não implementado")
        }
    context.startActivity(intent)
}
