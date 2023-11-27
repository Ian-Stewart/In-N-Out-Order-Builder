package repo

import kotlinx.coroutines.flow.Flow
import menuitems.CartItem

class CartRepository {
    val contents: Flow<List<CartItem>>
        get() = TODO()


    fun addItemToCart(item: CartItem) {

    }

    fun removeItemFromCart(id: String) {

    }
}