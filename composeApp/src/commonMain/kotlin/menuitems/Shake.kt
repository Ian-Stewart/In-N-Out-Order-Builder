package menuitems

data class Shake(
    val containsChocolate: Boolean = false,
    val containsVanilla: Boolean = true,
    val containsStrawberry: Boolean = false,
    val size: ShakeSize = ShakeSize.REGULAR,
    val splitIntoCups: Int = 1
): Item {
    override fun menuItemType() = MenuItemType.SHAKE
    override fun itemName(): String {
        val itemNameNoSplit = "${itemNameNoShakeNoCups()} Shake"
        return if (splitIntoCups > 1) {
            "$itemNameNoSplit (split into $splitIntoCups cups)"
        } else {
            itemNameNoSplit
        }
    }

    fun itemNameNoShakeNoCups(): String {
        val size = size.uiString
        val flavor = when {
            containsChocolate && containsVanilla && containsStrawberry -> "Neopolitan"
            containsChocolate && containsStrawberry -> "Strawberry-Chocolate"
            containsChocolate && containsVanilla -> "Chocolate-Vanilla"
            containsVanilla && containsStrawberry -> "Strawberry-Vanilla"
            containsChocolate -> "Chocolate"
            containsVanilla -> "Vanilla"
            else -> "Strawberry"
        }

        return "$size $flavor"
    }
}

enum class ShakeSize(val uiString: String) {
    REGULAR("Regular"),
    LARGE("Large"),
    EXTRA_LARGE("Extra Large")
}