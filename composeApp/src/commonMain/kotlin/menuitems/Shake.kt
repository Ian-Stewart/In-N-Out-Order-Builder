package menuitems

data class Shake(
    val containsChocolate: Boolean,
    val containsVanilla: Boolean,
    val containsStrawberry: Boolean,
    val size: ShakeSize,
    val splitIntoCups: Int = 1
): Item {
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