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
        val items = cart.cartItems.map { item -> OrderItem(name = item.item.itemName(), count = item.quantity)
        }.toMutableList()
        if (cart.spreadPackets > 0) {
            items.add(OrderItem(name = Extra.SPREAD_PACKET.itemName(), count = cart.spreadPackets))
        }
        if (cart.pepperPackets > 0) {
            items.add(OrderItem(name = Extra.PEPPER_PACKET.itemName(), count = cart.pepperPackets))
        }
        if (cart.pupPatties > 0) {
            items.add(OrderItem(name = Extra.PUP_PATTY.itemName(), count = cart.pupPatties))
        }
        mutableState.value = OrderViewState(orderItems = items)
    }
}

data class OrderViewState(
    val orderItems: List<OrderItem>
)

data class OrderItem(
    val name: String,
    val count: Int
)