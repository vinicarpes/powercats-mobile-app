package com.example.powercats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResolveAlertBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onCancelAlert: () -> Unit,
    sheetTitle: String,
    sheetText: String,
    visible: Boolean,
) {
    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = false,
        )
    val scope = rememberCoroutineScope()

    if (visible) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .background(Color.White)
                        .padding(16.dp),
            ) {
                Text(
                    text = sheetTitle,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Text(
                    text = sheetText,
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TextButton(
                        onClick = {
                            onDismissRequest()
                            scope.launch { sheetState.hide() }
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Voltar", fontSize = 18.sp)
                    }
                    Button(
                        onClick = {
                            onConfirmation()
                            scope.launch { sheetState.hide() }
                        },
                        modifier = Modifier.weight(1f),
                    ) {
                        Text("Confirmar", fontSize = 18.sp)
                    }
                }

                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                )

                TextButton(
                    onClick = {
                        onCancelAlert()
                        scope.launch { sheetState.hide() }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        tint = Color.Red,
                        contentDescription = "Ícone de lixeira",
                    )
                    Text(
                        text = "Cancelar alerta",
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
