package menuitems

/**
 * @property name - This is the user-readable string, meant for ordering
 * @property uuid - to identify items that may already be in the cart
 */
interface CartItem {
    fun name(): String
    fun uuid(): String
}