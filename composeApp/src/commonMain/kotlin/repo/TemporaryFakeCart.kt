package repo

import menuitems.Buns
import menuitems.Condiment
import menuitems.CondimentLevel
import menuitems.CondimentType
import menuitems.FloatDrink
import menuitems.FrenchFries
import menuitems.FryCookedLevels
import menuitems.Hamburger
import menuitems.MenuItemType
import menuitems.Shake
import menuitems.ShakeSize
import menuitems.SoftDrink
import menuitems.SoftDrinkSize
import menuitems.SoftDrinkType

class TemporaryFakeCart {
    companion object {
        val cart = Cart(
            spreadPackets = 2,
            pepperPackets = 5,
            pupPatties = 1,
            cartItems = listOf(
                CartItem(
                    item = Hamburger(
                        buns = Buns.STANDARD_TOAST,
                        condiments = listOf(
                            Condiment(
                                condimentType = CondimentType.LETTUCE,
                                level = CondimentLevel.STANDARD
                            ),
                            Condiment(
                                condimentType = CondimentType.MUSTARD,
                                level = CondimentLevel.EXTRA
                            ),
                            Condiment(
                                condimentType = CondimentType.PICKLES,
                                level = CondimentLevel.DOUBLE
                            )
                        ),
                        patties = 2,
                        slices = 2,
                        mustardFried = true,
                        extraWellDone = false
                    ),
                    quantity = 1
                ),
                CartItem(
                    item = FloatDrink(
                        softDrinkType = SoftDrinkType.COKE,
                        shake = Shake(containsChocolate = true, containsVanilla = true, containsStrawberry = true, size = ShakeSize.REGULAR)
                    ),
                    quantity = 1
                ),
                CartItem(
                    item = FrenchFries(
                        cookedLevels = FryCookedLevels.WELL_DONE,
                        condiments = listOf(Condiment(condimentType = CondimentType.SALT, level = CondimentLevel.EXTRA))
                    ),
                    quantity = 2
                ),
                CartItem(
                    item = Shake(
                        containsChocolate = true,
                        containsVanilla = false,
                        containsStrawberry = false,
                        size = ShakeSize.EXTRA_LARGE,
                        splitIntoCups = 1
                    ),
                    quantity = 3
                ),
                CartItem(
                    item = Shake(
                        containsChocolate = true,
                        containsVanilla = false,
                        containsStrawberry = true,
                        size = ShakeSize.LARGE,
                        splitIntoCups = 2
                    ),
                    quantity = 1
                ),
                CartItem(
                    item = SoftDrink(
                        type = SoftDrinkType.DIET_COKE,
                        size = SoftDrinkSize.SMALL
                    ),
                    quantity = 1
                ),
                CartItem(
                    item = SoftDrink(
                        type = SoftDrinkType.MILK,
                        size = SoftDrinkSize.EXTRA_LARGE
                    ),
                    quantity = 5
                )
            )
        )
    }
}