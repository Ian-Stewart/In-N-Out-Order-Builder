package menuitems

data class SoftDrink(
    val type: SoftDrinkType,
    val size: SoftDrinkSize
): Item {
    override fun itemName(): String {
        TODO("Not yet implemented")
    }
}

enum class SoftDrinkSize(val uiString: String) {
    SMALL("Small (12oz)"),
    MEDIUM("Medium (22oz)"),
    LARGE("Large (32oz)"),
    EXTRA_LARGE("Extra Large (40oz)")
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