package menuitems

data class FrenchFries(
    val cookedLevels: FryCookedLevels = FryCookedLevels.STANDARD,
    val condiments: List<Condiment> = listOf(),
): Item {
    override fun itemName(): String {
        val itemNameComponents = mutableListOf<String>()
        itemNameComponents.add("Fries")
        val isAnimalStyle = isAnimalStyle(condiments)
        if (isAnimalStyle) {
            itemNameComponents.add("Animal-Style")
        }
        if (cookedLevels != FryCookedLevels.STANDARD) {
            itemNameComponents.add(cookedLevels.uiString)
        }
        val animalStyleCondiments = setOf(CondimentType.PICKLES, CondimentType.SPREAD, CondimentType.GRILLED_ONIONS)
        condiments.forEach { condiment ->
            if (!isAnimalStyle || condiment.condimentType !in animalStyleCondiments) {
                itemNameComponents.add("${condiment.level.uiString} ${condiment.condimentType.uiString}")
            }
        }
        return itemNameComponents.joinToString(", ")
    }
}