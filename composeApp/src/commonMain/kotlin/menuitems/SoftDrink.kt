package menuitems

data class SoftDrink(
    val type: SoftDrinkType = SoftDrinkType.COKE,
    val size: SoftDrinkSize = SoftDrinkSize.MEDIUM
): Item {
    override fun itemName(): String {
        return "${size.uiString} ${type.uiString}"
    }
}

enum class SoftDrinkSize(val uiString: String, val description: String) {
    SMALL("Small", "12 oz"),
    MEDIUM("Medium", "22 oz"),
    LARGE("Large", "32 oz"),
    EXTRA_LARGE("Extra Large", "40 oz")
}

enum class SoftDrinkType(val uiString: String, val description: String? = null) {
    COKE("Coke"),
    DIET_COKE("Diet Coke"),
    SEVEN_UP("7up"),
    PINK_LEMONADE("Pink Lemonade"),
    LIGHT_LEMONADE("Light Lemonade", "Zero Sugar Minute Maid"),
    SWEET_TEA("Sweet Tea", "(Texas Stores Only)"),
    ICED_TEA("Iced Tea"),
    DR_PEPPER("Dr. Pepper"),
    ROOT_BEER("Root Beer"),
    MILK("Milk"),
    COFFEE("Coffee"),
    HOT_COCOA("Hot Cocoa")
}