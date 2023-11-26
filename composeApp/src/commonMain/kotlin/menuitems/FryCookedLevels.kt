package menuitems

enum class FryCookedLevels(val uiString: String, val warning: String? = null) {
    LIGHT("Light", "May be very lightly cooked. You probably do not want this."),
    STANDARD("Standard"),
    LIGHT_WELL("Light Well Done"),
    WELL_DONE("Well Done"),
    EXTRA_WELL("Extra Well Done", "May be extremely dark or burned!")
}