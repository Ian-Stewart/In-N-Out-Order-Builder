package menuitems

/**
 * Represents any kind of sandwich
 *
 * @property buns [Buns]
 * @property patties [Int]
 * @property slices [Int]
 * @property mustardFried [Boolean] - only meaningful if patties > 0
 * @property extraWellDone [Boolean] - only meaningful if patties > 0
 * @property condiments [List] of [Condiment]
 *
 * Grilled cheese - no patties
 * Flying dutchman - no buns
 * Protein Style - lettuce buns
 */
data class Hamburger(
    val buns: Buns,
    val patties: Int,
    val mustardFried: Boolean,
    val extraWellDone: Boolean,
    val slices: Int,
    val condiments: List<Condiment>
): CartItem {
    override fun itemName(): String {
        TODO("Not yet implemented")
    }
}
