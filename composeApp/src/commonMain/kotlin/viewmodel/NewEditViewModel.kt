package viewmodel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import menuitems.FloatDrink
import menuitems.FrenchFries
import menuitems.Hamburger
import menuitems.MenuItemType
import menuitems.Shake
import menuitems.SoftDrink
import repo.Cart
import repo.CartItem
import repo.CartRepository

/**
 * Given a CartItem, edit it and then pass it into the repo
 */
class NewEditViewModel(
    private val cartRepository: CartRepository,
    private val onNewOrEdit: () -> Unit,
    private val onDone: () -> Unit,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private val mutableState = MutableStateFlow(NewEditViewState())

    private var lastCart: Cart = Cart()
    private var currentItem: CartItem? = null

    val stateFlow: StateFlow<NewEditViewState>
        get() = mutableState.asStateFlow()

    init {
        scope.launch { cartRepository.cart.collect{ onNewCart(it) } }
    }

    private fun onNewCart(cart: Cart) {
        lastCart = cart
    }

    fun onEvent(event: NewEditEvent) {
        when (event) {
            is NewEditEvent.EditItemEvent -> onEditItemEvent(event)
            is NewEditEvent.NewItemEvent -> onNewItemEvent(event)
            is NewEditEvent.QuantityEvent -> onQuantityEvent(event)
            is NewEditEvent.DoneEvent -> onDoneEvent()
            is NewEditEvent.CancelEvent -> onCancelEvent()
        }
    }

    private fun onEditItemEvent(event: NewEditEvent.EditItemEvent) {
        val item = lastCart.cartItems.firstOrNull { it.id == event.cartItemUUID }
        resultToViewState(NewEditEventResult.EditItemEventResult(item))
        onNewOrEdit()
    }

    private fun onNewItemEvent(event: NewEditEvent.NewItemEvent) {
        val newCartItem: CartItem = when (event.itemType) {
            MenuItemType.BURGER -> CartItem(item = Hamburger())
            MenuItemType.SHAKE -> CartItem(item = Shake())
            MenuItemType.SODY_POP -> CartItem(item = SoftDrink())
            MenuItemType.FLOAT -> CartItem(item = FloatDrink())
            MenuItemType.FRIES -> CartItem(item = FrenchFries())
        }
        currentItem = newCartItem
        resultToViewState(NewEditEventResult.NewItemEventResult(newCartItem))
        onNewOrEdit()
    }

    private fun onQuantityEvent(event: NewEditEvent.QuantityEvent) {
        val oldItem = currentItem ?: return
        val editedItem = oldItem.copy(quantity = event.quantity)
        currentItem = editedItem
        resultToViewState(NewEditEventResult.EditItemEventResult(editedItem))
    }

    /**
     * Save the cart item and close
     */
    private fun onDoneEvent() {
        val item = currentItem ?: run {
            onDone()
            return
        }
        currentItem = null
        val state = mutableState.value
        if (state.newItem) {
            cartRepository.addNewItemToCart(item)
        } else {
            cartRepository.editExistingCartitem(item)
        }
        onDone()
    }

    /**
     * Closes without saving to the repo
     */
    private fun onCancelEvent() {
        currentItem = null
        onDone()
    }

    private fun resultToViewState(result: NewEditEventResult) {
        mutableState.value =  when (result) {
            is NewEditEventResult.NewItemEventResult -> {
                mutableState.value.copy(
                    item = result.cartItem,
                    newItem = true,
                    error = false
                )
            }
            is NewEditEventResult.EditItemEventResult -> {
                mutableState.value.copy(
                    item = result.cartItem,
                    newItem = false,
                    error = result.cartItem == null
                )
            }
        }
    }
}

/**
 * [item]: [CartItem] The selected cart item
 * [newItem]: [Boolean] True if this is a new item
 * [error]: [Boolean] True if there was an error
 */
data class NewEditViewState(
    val item: CartItem? = null,
    val newItem: Boolean = true,
    val error : Boolean = false
)

sealed class NewEditEvent {
    data class NewItemEvent(val itemType: MenuItemType): NewEditEvent()
    data class EditItemEvent(val cartItemUUID: String): NewEditEvent()
    data class QuantityEvent(val quantity: Int): NewEditEvent()
    data object DoneEvent: NewEditEvent()
    data object CancelEvent: NewEditEvent()
}

sealed class NewEditEventResult {
    data class NewItemEventResult(val cartItem: CartItem): NewEditEventResult()
    data class EditItemEventResult(val cartItem: CartItem?): NewEditEventResult()
}