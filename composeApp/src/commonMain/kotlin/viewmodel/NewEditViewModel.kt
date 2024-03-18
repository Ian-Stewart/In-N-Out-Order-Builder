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
import menuitems.Item
import menuitems.MenuItemType
import menuitems.Shake
import menuitems.SoftDrink
import repo.Cart
import repo.CartItem
import repo.CartRepository

/**
 * Given a CartItem, edit it and then pass it into the repo
 * Note: This handles all item types (fries, soda, etc) and doesn't have any fancy logic in it
 * It just takes whatever changes the user made in the UI and saves it
 * In a real, not-educational app this would probably be broken down into viewmodels/subviewmodels
 * But I am not trying to create an enterprise app here, just bang something out to learn a bit :)
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
    private var newItem = false

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
            is NewEditEvent.EditExistingItemEvent -> onEditItemEvent(event)
            is NewEditEvent.CreateNewItemEvent -> onNewItemEvent(event)
            is NewEditEvent.UpdateCurrentItemEvent -> onUpdateCurrentItemEvent(event)
            is NewEditEvent.QuantityEvent -> onQuantityEvent(event)
            is NewEditEvent.DoneEvent -> onDoneEvent()
            is NewEditEvent.CancelEvent -> onCancelEvent()
        }
    }

    private fun onEditItemEvent(event: NewEditEvent.EditExistingItemEvent) {
        newItem = false
        val item = lastCart.cartItems.firstOrNull { it.id == event.cartItemUUID }
        currentItem = item
        resultToViewState(NewEditEventResult.EditItemEventResult(item))
        onNewOrEdit()
    }

    private fun onNewItemEvent(event: NewEditEvent.CreateNewItemEvent) {
        newItem = true
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

    private fun onUpdateCurrentItemEvent(event: NewEditEvent.UpdateCurrentItemEvent) {
        newItem = false
        val cartItem = currentItem ?: run {
            resultToViewState(NewEditEventResult.EditItemEventResult(cartItem = null))
            return
        }
        currentItem = cartItem.copy(item = event.item)
        resultToViewState(NewEditEventResult.EditItemEventResult(cartItem))
    }

    private fun onQuantityEvent(event: NewEditEvent.QuantityEvent) {
        val oldItem = currentItem ?: return
        val editedItem = oldItem.copy(quantity = event.quantity.coerceAtLeast(1).coerceAtMost(99))
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
                    newItem = newItem,
                    error = false
                )
            }
            is NewEditEventResult.EditItemEventResult -> {
                mutableState.value.copy(
                    item = result.cartItem,
                    newItem = newItem,
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
    /**
     * The user wishes to create a new item of a given type
     */
    data class CreateNewItemEvent(val itemType: MenuItemType): NewEditEvent()

    /**
     * The user wants to make a change to an existing item in their cart
     */
    data class EditExistingItemEvent(val cartItemUUID: String): NewEditEvent()

    /**
     * The user has made a change to an existing item that we are already displaying
     */
    data class UpdateCurrentItemEvent(val item: Item): NewEditEvent()

    /**
     * The user has edited the quantity of an item that we are already displaying
     */
    data class QuantityEvent(val quantity: Int): NewEditEvent()

    /**
     * The user is done making changes to the item we are displaying
     * and wants to go back to what they were doing before
     */
    data object DoneEvent: NewEditEvent()

    /**
     * The user has decided they don't want to add the item they were adding before
     */
    data object CancelEvent: NewEditEvent()
}

sealed class NewEditEventResult {
    data class NewItemEventResult(val cartItem: CartItem): NewEditEventResult()
    data class EditItemEventResult(val cartItem: CartItem?): NewEditEventResult()
}