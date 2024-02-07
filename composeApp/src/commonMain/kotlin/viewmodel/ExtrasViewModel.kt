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
            extraSpread = cart.extraSpread
        )
    }
}

data class ExtrasViewState(
    val pupPatties: Int = 0,
    val pepperPackets: Int = 0,
    val extraSpread: Int = 0
)