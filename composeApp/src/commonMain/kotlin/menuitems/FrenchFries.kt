package menuitems

data class FrenchFries(
    val cookedLevels: FryCookedLevels = FryCookedLevels.STANDARD,
    val extras: List<Condiment>,
): CartItem {
    override fun name(): String {
        TODO("Not yet implemented")
    }
}