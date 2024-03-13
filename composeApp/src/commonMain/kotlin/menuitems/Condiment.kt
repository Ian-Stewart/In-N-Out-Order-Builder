package menuitems

data class Condiment(
    val condimentType: CondimentType,
    val level: CondimentLevel
) {
    fun toOrderString(): String {
        return "${level.uiString} ${condimentType.uiString}"
    }
}

enum class CondimentLevel(val uiString: String) {
    NONE("None"),
    VERY_LIGHT("Very Light"),
    LIGHT("Light"),
    STANDARD("Standard"),
    EXTRA("Extra"),
    DOUBLE("Double")
}

enum class CondimentType(val uiString: String, val validOnFries: Boolean = true, val description: String? = null) {
    SPREAD("Spread", true, "In-N-Out spread is similar to Thousand Island Dressing. It's a blend of Ketchup, Mayo, Relish, and Sugar."),
    LETTUCE("Lettuce", true, "Torn, not shredded"),
    TOMATO("Tomato"),
    PICKLES("Pickle Slices"),
    KETCHUP("Ketchup"),
    MUSTARD("Mustard"),
    SALT("Salt"),
    CHILES("Chiles", true, "Chopped Cascabella (yellow) peppers. Not too hot. Costs a little extra on fries, but not on a burger"),
    REGULAR_ONIONS("Sliced Onions", false),
    GRILLED_ONIONS("Grilled Sliced Onions"),
    RAW_ONION("Raw Sliced Onions"),
    WHOLE_GRILLED_ONION("Whole Grilled Onion", false, "This is a whole slice of onion, grilled"),
    CHOPPED_ONION("Chopped Onion", false),
    RAW_CHOPPED_ONION("Raw Chopped Onion"),
}
