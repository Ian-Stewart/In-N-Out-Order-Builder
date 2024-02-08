package repo

import menuitems.Item
import menuitems.MenuItemType
import utils.UUIDGenerator

/**
 * Represents elements in the shopping cart
 * @property item [Item] the specific item, with descriptors such as toppings, etc
 * @property quantity [Int] the number of this item in the cart
 * @property id [String] a uuid
 */
data class CartItem(
    val item: Item,
    val quantity: Int = 1,
    val id: String = UUIDGenerator()
)
