package viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menuitems.Extra
import repo.Cart
import repo.CartRepository

class OrderViewModel(
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private val mutableState : MutableStateFlow<OrderViewState> = MutableStateFlow(OrderViewState(listOf()))
    val stateFlow: StateFlow<OrderViewState>
        get() = mutableState.asStateFlow()

    init {
        // Using generated Scope and the dispatcher from the repo (generally, this is fine)
        //cartRepository.cart.onEach { withContext(dispatcher) { onNewCart(it) } }
        // Another way to do it...
        scope.launch { cartRepository.cart.collect { onNewCart(it) } }
    }

    private fun onNewCart(cart: Cart) {
        val items = cart.cartItems.map { item ->
            "${item.quantity} X ${item.item.itemName()}"
        }.toMutableList()
        if (cart.spreadPackets > 0) {
            items.add("${cart.spreadPackets} X ${Extra.SPREAD_PACKET.itemName()}")
        }
        if (cart.pepperPackets > 0) {
            items.add("${cart.pepperPackets} X ${Extra.PEPPER_PACKET.itemName()}")
        }
        if (cart.pupPatties > 0) {
            items.add("${cart.pupPatties} X ${Extra.PUP_PATTY.itemName()}")
        }
        mutableState.value = OrderViewState(orderItems = items)
    }
}

data class OrderViewState(
    val orderItems: List<String>
)