package menuitems

data class Float(
    val softDrinkType: SoftDrinkType,
    val shake: Shake
): Item {

    /**
     * Size is carried in the Shake Size parameter
     * As far as I know, you can't split a float into multiple cups (also, this seems obnoxious to do?)
     */
    override fun itemName(): String {
        val flavor = shake.itemNameNoShakeNoCups()
        val soda = softDrinkType.uiString
        val size = shake.size.uiString
        return "$size $flavor $soda Float"
    }
}
