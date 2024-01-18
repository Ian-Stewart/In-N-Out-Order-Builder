package menuitems

data class Shake(
    val containsChocolate: Boolean,
    val containsVanilla: Boolean,
    val containsStrawberry: Boolean,
    val size: ShakeSize,
    val splitIntoCups: Int = 1
): CartItem {
    override fun itemName(): String {
        TODO("Not yet implemented")
    }
}

enum class ShakeSize(val uiString: String) {
    REGULAR("Regular"),
    LARGE("Large"),
    EXTRA_LARGE("Extra Large")
}