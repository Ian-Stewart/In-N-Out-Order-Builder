package repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartRepository {
    private val tempFakeCart = TemporaryFakeCart.cart
    private val contents: MutableStateFlow<Cart> = MutableStateFlow(tempFakeCart)
    val cart: Flow<Cart>
        get() = contents.asStateFlow()

    fun addNewItemToCart(cartItem: CartItem) {
        contents.value = contents.value.copy(
            cartItems = contents.value.cartItems.plus(cartItem)
        )
    }

    fun editExistingCartitem(cartItem: CartItem) {
        contents.value = contents.value.copy(
            cartItems = contents.value.cartItems.map { ci ->
                if (ci.id == cartItem.id) {
                    ci.copy(
                        quantity = cartItem.quantity,
                        item = cartItem.item
                    )
                } else {
                    ci
                }
            }
        )
    }

    fun setPepperPacketsQuantity(packets: Int) {
        contents.value = contents.value.copy(
            pepperPackets = packets
        )
    }

    fun setExtraSpreadQuantity(extraSpread: Int) {
        contents.value = contents.value.copy(
            spreadPackets = extraSpread
        )
    }

    fun setPupPattyQuantity(pupPatties: Int) {
        contents.value = contents.value.copy(
            pupPatties = pupPatties
        )
    }

    /**
     * Remove a given item from the cart, regardless of quantity
     */
    fun removeItemFromCart(cartItem: CartItem) {
        contents.value = contents.value.copy(
            cartItems = contents.value.cartItems.filterNot { it.id == cartItem.id }
        )
    }
}

data class Cart(
    val cartItems: List<CartItem> = listOf(),
    val pupPatties: Int = 0,
    val spreadPackets: Int = 0,
    val pepperPackets: Int = 0
)