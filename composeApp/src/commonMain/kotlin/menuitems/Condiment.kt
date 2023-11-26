package menuitems

data class Condiment(
    val condimentType: CondimentType,
    val level: CondimentLevel
)

enum class CondimentLevel(val uiString: String) {
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
    REGULAR_ONIONS("Sliced Onions", false),
    GRILLED_ONIONS("Grilled Sliced Onions"),
    PICKLES("Pickle Slices"),
    RAW_ONION("Raw Sliced Onions"),
    WHOLE_GRILLED_ONION("Whole Grilled Onion", false, "This is a whole slice of onion, grilled"),
    CHOPPED_ONION("Chopped Onion", false),
    RAW_CHOPPED_ONION("Raw Chopped Onion"),
    KETCHUP("Ketchup"),
    MUSTARD("Mustard"),
    CHILES("Chiles", true, "Chopped Cascabella (yellow) peppers. Not too hot. Costs a little extra on fries, but not on a burger"),
    SALT("Salt")
}
