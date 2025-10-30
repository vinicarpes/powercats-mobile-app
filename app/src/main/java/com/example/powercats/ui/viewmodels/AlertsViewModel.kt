import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.powercats.ui.model.AlertUi
import com.example.powercats.ui.model.EAlertStatus
import com.example.powercats.usecases.AlertUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlertsViewModel(
    private val useCase: AlertUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AlertsState>(AlertsState.Loading)
    val state: StateFlow<AlertsState> = _state

    init {
        getAlerts()
    }

    fun getAlerts() {
        viewModelScope.launch {
            _state.value = AlertsState.Loading
            val result = useCase.getAlerts()
            _state.value =
                result.fold(
                    onSuccess = { AlertsState.Success(it) },
                    onFailure = { AlertsState.Error(it.message ?: "Erro desconhecido") },
                )
        }
    }

    fun updateAlertStatus(
        alertUi: AlertUi,
        status: EAlertStatus,
    ) {
        viewModelScope.launch {
            val result = useCase.updateAlertStatus(alertUi.id, status)
            result.fold(
                onSuccess = {
                    getAlerts()
                },
                onFailure = {
                    _state.value = AlertsState.Error(it.message ?: "Erro ao atualizar")
                },
            )
        }
    }

    sealed interface AlertsState {
        object Loading : AlertsState

        data class Success(
            val alerts: List<AlertUi>,
        ) : AlertsState

        data class Error(
            val message: String,
        ) : AlertsState

        data class Updated(
            val alertUi: AlertUi,
        ) : AlertsState
    }
}
