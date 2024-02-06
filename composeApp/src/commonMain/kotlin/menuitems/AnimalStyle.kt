package menuitems

/**
 * Extra Spread, Grilled onions, Pickles
 * Note that for burgers, this also implies mustard fried
 */
fun isAnimalStyle(condiments: List<Condiment>): Boolean {
    var hasExtraSpread = false
    var hasGrilledOnions = false
    var hasPickles = false
    condiments.forEach { condiment ->
        hasExtraSpread = hasExtraSpread || (condiment.condimentType == CondimentType.SPREAD && condiment.level == CondimentLevel.EXTRA)
        hasGrilledOnions = hasGrilledOnions || (condiment.condimentType == CondimentType.GRILLED_ONIONS && condiment.level == CondimentLevel.STANDARD)
        hasPickles = hasPickles || (condiment.condimentType == CondimentType.GRILLED_ONIONS && condiment.level == CondimentLevel.STANDARD)
    }
    return hasExtraSpread && hasGrilledOnions && hasPickles
}