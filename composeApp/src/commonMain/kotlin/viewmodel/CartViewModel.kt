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

class CartViewModel(
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private val mutableState = MutableStateFlow(CartViewState())

    private var onEditItem: (String) -> Unit = {}
    private var onEditExtras: () -> Unit = {}

    val stateFlow: StateFlow<CartViewState>
        get() = mutableState.asStateFlow()

    init {
        scope.launch { cartRepository.cart.collect{ onNewCart(it) } }
    }

    // TODO there's probably a nicer way to do this, but it's not coming to me this morning
    // I don't think I want my DI messing with nav?
    // Will need to read more voyager docs
    // Still, this is nicer than the spaghetti in App.kt
    fun bindOnEditItem(onEdit: (String) -> Unit) {
        onEditItem = onEdit
    }

    // TODO See comment above
    fun bindOnEditExtras(onExtras: () -> Unit) {
        onEditExtras = onExtras
    }

    private fun onNewCart(cart: Cart) {
        mutableState.value = CartViewState(cart)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            CartEvent.EditExtrasEvent -> onEditExtras()
            is CartEvent.EditItemEvent -> onEditItem(event.cartItemUUID)
        }
    }
}

data class CartViewState(
    val cart: Cart = Cart()
)

sealed class CartEvent {
    data object EditExtrasEvent: CartEvent()
    data class EditItemEvent(val cartItemUUID: String): CartEvent()
}