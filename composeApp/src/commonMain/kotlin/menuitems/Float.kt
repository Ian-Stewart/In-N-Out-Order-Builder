package menuitems

data class Float(
    val softDrink: SoftDrink,
    val shake: Shake
): CartItem {
    override fun itemName(): String {
        TODO("Not yet implemented")
    }
}
