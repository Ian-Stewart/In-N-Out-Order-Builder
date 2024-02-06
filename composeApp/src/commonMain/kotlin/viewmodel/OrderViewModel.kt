package viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import menuitems.Extra
import repo.CartItem
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

    private fun onNewCart(cart: List<CartItem>) {
        val items = cart.map { item ->
            "${item.quantity} X ${item.item.itemName()}"
        }
        mutableState.value = OrderViewState(items)
    }
}

data class OrderViewState(
    val orderItems: List<String>
)