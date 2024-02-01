package repo

import menuitems.Item

/**
 * Represents elements in the shopping cart
 * @property item [Item]
 * @property quantity [Int]
 */
data class CartItem(
    val item: Item,
    val quantity: Int
)
