package menuitems

data class Float(
    val softDrink: SoftDrink,
    val shake: Shake
): CartItem {
    override fun name(): String {
        TODO("Not yet implemented")
    }
}
