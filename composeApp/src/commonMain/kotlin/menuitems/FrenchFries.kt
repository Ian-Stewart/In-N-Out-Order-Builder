package menuitems

data class FrenchFries(
    val cookedLevels: FryCookedLevels = FryCookedLevels.STANDARD,
    val extras: List<Condiment>,
)