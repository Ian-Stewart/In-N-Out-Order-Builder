package viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repo.Cart
import repo.CartRepository

class ExtrasViewModel(
    private val cartRepository: CartRepository,
    private val onDone: () -> Unit,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val scope = CoroutineScope(Job() + dispatcher)
    private val mutableState = MutableStateFlow(ExtrasViewState())

    val stateFlow: StateFlow<ExtrasViewState>
        get() = mutableState.asStateFlow()

    init {
        scope.launch { cartRepository.cart.collect{ onNewCart(it) } }
    }

    private fun onNewCart(cart: Cart) {
        mutableState.value = ExtrasViewState(
            pupPatties = cart.pupPatties,
            pepperPackets = cart.pepperPackets,
            spreadPackets = cart.extraSpread
        )
    }

    fun onEvent(event: ExtrasEvent) {
        when (event) {
            ExtrasEvent.CloseViewEvent -> onDone()
            is ExtrasEvent.SetExtraSpreadEvent -> onSetExtraSpreadEvent(event)
            is ExtrasEvent.SetPepperPacketsEvent -> onSetPepperPacketsEvent(event)
            is ExtrasEvent.SetPupPattiesEvent -> onSetPupPattiesEvent(event)
        }
    }

    private fun onSetExtraSpreadEvent(event: ExtrasEvent.SetExtraSpreadEvent) {
        cartRepository.setExtraSpreadQuantity(event.extraSpread)
    }

    private fun onSetPepperPacketsEvent(event: ExtrasEvent.SetPepperPacketsEvent) {
        cartRepository.setPepperPacketsQuantity(event.pepperPackets)
    }

    private fun onSetPupPattiesEvent(event: ExtrasEvent.SetPupPattiesEvent) {
        cartRepository.setPupPattyQuantity(event.pupPatties)
    }
}

data class ExtrasViewState(
    val pupPatties: Int = 0,
    val pepperPackets: Int = 0,
    val spreadPackets: Int = 0
)

sealed class ExtrasEvent {
    data object CloseViewEvent: ExtrasEvent()
    data class SetPepperPacketsEvent(val pepperPackets: Int): ExtrasEvent()
    data class SetExtraSpreadEvent(val extraSpread: Int): ExtrasEvent()
    data class SetPupPattiesEvent(val pupPatties: Int): ExtrasEvent()
}