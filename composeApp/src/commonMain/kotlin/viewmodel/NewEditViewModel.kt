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
import repo.CartItem
import repo.CartRepository

/**
 * Given a CartItem, edit it and then pass it into the repo
 */
class NewEditViewModel(
    private val cartRepository: CartRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private val mutableState = MutableStateFlow(NewEditViewState())

    private var lastCart: List<CartItem> = listOf()

    val stateFlow: StateFlow<NewEditViewState>
        get() = mutableState.asStateFlow()

    init {
        scope.launch { cartRepository.cart.collect{ onNewCart(it) } }
    }

    /**
     * We just want to store the last cart we got, in case we get a request to edit it later
     * We blow up the viewstate since the world has changed under us. We should expect to see
     * a request to open an existing cart item (or make a new one) later, so this is fine.
     */
    private fun onNewCart(cart: List<CartItem>) {
        mutableState.value = NewEditViewState(
            item = null,
            newItem = true
        )
        lastCart = cart
    }

    fun onEvent(event: NewEditEvent) {
        when (event) {
            is NewEditEvent.EditItemEvent -> onEditItemEvent(event)
            is NewEditEvent.NewItemEvent -> onNewItemEvent(event)
        }
    }

    private fun onEditItemEvent(event: NewEditEvent.EditItemEvent) {
        val item = lastCart.firstOrNull { it.id == event.cartItemUUID }
        resultToViewState(NewEditEventResult.EditItemEventResult(item))
    }

    private fun onNewItemEvent(event: NewEditEvent.NewItemEvent) {
        val newCartItem: CartItem = when (event.itemType) {
            MenuItemType.BURGER -> CartItem(item = Hamburger())
            MenuItemType.SHAKE -> CartItem(item = Shake())
            MenuItemType.SODY_POP -> CartItem(item = SoftDrink())
            MenuItemType.FLOAT -> CartItem(item = FloatDrink())
            MenuItemType.FRIES -> CartItem(item = FrenchFries())
        }
        resultToViewState(NewEditEventResult.NewItemEventResult(newCartItem))
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
}

sealed class NewEditEventResult {
    data class NewItemEventResult(val cartItem: CartItem): NewEditEventResult()
    data class EditItemEventResult(val cartItem: CartItem?): NewEditEventResult()
}