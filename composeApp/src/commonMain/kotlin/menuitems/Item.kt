package menuitems

/**
 * @property itemName [String] This is the user-readable string, meant for ordering
 * @property menuItemType [MenuItemType] The rough category for this item
 */
interface Item {
    fun itemName(): String
    fun menuItemType(): MenuItemType
}