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
    val buns: Buns = Buns.STANDARD_TOAST,
    val patties: Int = 1,
    val mustardFried: Boolean = false,
    val extraWellDone: Boolean = false,
    val slices: Int = 0,
    val condiments: List<Condiment> = listOf()
): Item {
    override fun itemName(): String {
        val isGrilledCheese = slices > 0 && patties == 0
        val orderList = mutableListOf<String>()
        // Generate the burger type
        if (slices == 0) {
            // Hamburger
            when (patties) {
                0 -> orderList.add("Toast (I'm pretty sure you can't do this..??)")
                1 -> orderList.add("Hamburger")
                2 -> orderList.add("Double Meat")
                3 -> orderList.add("Triple Meat")
                4 -> orderList.add("Quadruple Meat")
                else -> orderList.add("${patties}x0")
            }
        } else {
            if (patties == 2 && slices == 2) {
                if (isFlyingDutchman()) {
                    orderList.add("Flying Dutchman")
                } else {
                    orderList.add("Double-Double")
                }
            } else {
                if (isGrilledCheese) {
                    orderList.add("Grilled Cheese")
                } else {
                    orderList.add("${patties}x${slices}")
                }
            }
        }
        // Add primary descriptors (these need to go before toppings, as modifying these clears the condiment preferences in the ordering system)
        if (isAnimalStyle()) {
            orderList.add("Animal Style")
        } else if (mustardFried) {
            // Animal style is mustard fried
            orderList.add("Mustard Fried")
        }
        if (extraWellDone) {
            orderList.add("Extra Well Done")
        }
        // Bun type
        val bunDescriptor = when (buns) {
            Buns.NO_BUNS -> if (isFlyingDutchman()) { "" } else { "No Buns" } // (flying dutchman is also "No buns")
            Buns.LETTUCE_BUNS -> "Protein Style"
            Buns.UNTOASTED -> "Untoasted Buns"
            Buns.STANDARD_TOAST -> "" // Default Option
            Buns.EXTRA_TOASTED -> "Extra Toasted Buns"
        }
        if (bunDescriptor.isNotEmpty()) {
            orderList.add(bunDescriptor)
        }
        // Condiments
        // Mustard Fried defaults with pickles, so if they were removed we want to indicate that verbally
        if (mustardFried && condiments.firstOrNull { it.condimentType == CondimentType.PICKLES } == null) {
            orderList.add("No Pickles")
        }
        // Indicate if other standard condiments were removed
        val standardCondiments = setOf(CondimentType.LETTUCE, CondimentType.TOMATO, CondimentType.SPREAD)
        standardCondiments.forEach { standardCondiment ->
            if (condiments.firstOrNull { it.condimentType == standardCondiment } == null) {
                orderList.add("No ${standardCondiment.uiString}")
            }
        }
        condiments.forEach { condiment ->
            if (!isAnimalStyle() && condiment.condimentType in setOf(CondimentType.SPREAD, CondimentType.GRILLED_ONIONS, CondimentType.PICKLES)) {
                // We can bypass these, as animal style places restrictions on how much of these condiments you can order
                // Ex, if there was no pickles or double grilled onions, it wouldn't be animal style
                // This condition could be inverted by De Morgan's Law to omit the empty branch
                // but in my opinion this is easier to read, I think?
            } else {
                // If it's standard pickles on mustard fried patties, we wanna skip it (that's the default)
                var shouldAddCondiment: Boolean = condiment.condimentType != CondimentType.PICKLES || condiment.level != CondimentLevel.STANDARD || !mustardFried
                // Standard lettuce/tomato/pickles come on all burgers
                // so if it's one of these we only want to add it if the level is different
                shouldAddCondiment = shouldAddCondiment && (condiment.condimentType !in standardCondiments || condiment.level != CondimentLevel.STANDARD)
                // Grilled cheese comes with no toppings by default
                shouldAddCondiment = shouldAddCondiment || isGrilledCheese
                if (shouldAddCondiment) {
                    orderList.add(condiment.toOrderString())
                }
            }
        }
        return orderList.joinToString(", ")
    }

    /**
     * Extra spread, grilled onions, pickles, mustard fried patty
     */
    private fun isAnimalStyle(): Boolean {
        return if (!mustardFried) {
            false
        } else {
            isAnimalStyle(condiments)
        }
    }

    /**
     * Allegedly this *can* be ordered with condiments
     */
    private fun isFlyingDutchman(): Boolean {
        return buns == Buns.NO_BUNS && patties == 2 && slices == 2
    }
}
