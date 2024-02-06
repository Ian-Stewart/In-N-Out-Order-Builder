package repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import menuitems.Buns
import menuitems.Condiment
import menuitems.CondimentLevel
import menuitems.CondimentType
import menuitems.Hamburger
import menuitems.Item

class CartRepository {
    private val tempFakeCart = listOf(
        CartItem(
            item = Hamburger(
                buns = Buns.STANDARD_TOAST,
                condiments = listOf(
                    Condiment(
                        condimentType = CondimentType.LETTUCE,
                        level = CondimentLevel.STANDARD
                    ),
                    Condiment(
                        condimentType = CondimentType.MUSTARD,
                        level = CondimentLevel.EXTRA
                    ),
                    Condiment(
                        condimentType = CondimentType.PICKLES,
                        level = CondimentLevel.DOUBLE
                    )
                ),
                patties = 2,
                slices = 2,
                mustardFried = true,
                extraWellDone = false
            ),
            quantity = 1
        ),

    )
    private val contents: MutableStateFlow<List<CartItem>> = MutableStateFlow(tempFakeCart)
    val cart: Flow<List<CartItem>>
        get() = contents.asStateFlow()


    fun addItemToCart(item: Item) {
        var added = false
        contents.value = contents.value.map { ci ->
            if (ci.item.itemName() == item.itemName()) {
                added = true
                ci.copy(quantity = ci.quantity.plus(1))
            } else {
                ci
            }
        }
        if (!added) {
            contents.value = contents.value.plus(CartItem(item, 1))
        }
    }

    fun adjustCartQuanity(item: Item, newQuantity: Int) {
        contents.value = contents.value.map { ci ->
            if (ci.item.itemName() == item.itemName()) {
                ci.copy(quantity = newQuantity)
            } else {
                ci
            }
        }
    }

    /**
     * Remove a given item from the cart, regardless of quantity
     */
    fun removeItemFromCart(item: Item) {
        contents.value = contents.value.filterNot { it.item.itemName() == item.itemName() }
    }
}