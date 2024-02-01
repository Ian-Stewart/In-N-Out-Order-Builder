package menuitems

data class FrenchFries(
    val cookedLevels: FryCookedLevels = FryCookedLevels.STANDARD,
    val extras: List<Condiment>,
): Item {
    override fun itemName(): String {
        TODO("Not yet implemented")
    }
}