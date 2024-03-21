package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import constants.Dims
import menuitems.Buns
import menuitems.Condiment
import menuitems.CondimentLevel
import menuitems.FloatDrink
import menuitems.FrenchFries
import menuitems.FryCookedLevels
import menuitems.Hamburger
import menuitems.Shake
import menuitems.ShakeSize
import menuitems.SoftDrink
import menuitems.SoftDrinkSize
import menuitems.SoftDrinkType
import repo.CartItem
import viewmodel.NewEditEvent
import views.pickers.BooleanPicker
import views.pickers.MultiPicker
import views.pickers.MultiPickerOption
import views.pickers.QuantitySelector


@Composable
fun ItemDetail(
    cartItem: CartItem,
    isNewItem: Boolean,
    eventHandler: (NewEditEvent) -> Unit
) {
    Dialog(
        onDismissRequest = { eventHandler(NewEditEvent.CancelEvent) },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
            )
    ) {
        ItemDetailWithinDialog(cartItem, isNewItem, eventHandler)
    }
}

@Composable
private fun ItemDetailWithinDialog(
    cartItem: CartItem,
    isNewItem: Boolean,
    eventHandler: (NewEditEvent) -> Unit
) {
    val bottomBarHeight = 60.dp
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {},
        content = {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier.fillMaxHeight().verticalScroll(scrollState).padding(Dims.smPad),
                verticalArrangement = Arrangement.SpaceBetween) {

                Text(
                    text = if (isNewItem) { "Add" } else { "Edit" },
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(Dims.smPad)
                )
                // Name
                Text(text = cartItem.item.itemName(), modifier = Modifier.padding(Dims.smPad))

                QuantitySelector(
                    titleString = "Quantity",
                    currentQuantity = cartItem.quantity,
                    onQuantityChanged = { quantity: Int -> eventHandler(NewEditEvent.QuantityEvent(quantity)) }
                )

                when (cartItem.item) {
                    is Hamburger -> HamburgerSection(hamburger = cartItem.item, eventHandler = eventHandler)
                    is FrenchFries -> FrySection(frenchFries = cartItem.item, eventHandler = eventHandler)
                    is FloatDrink -> FloatSection(float = cartItem.item, eventHandler = eventHandler)
                    is SoftDrink -> SoftDrinkSection(softDrink = cartItem.item, eventHandler = eventHandler)
                    is Shake -> ShakeSection(shake = cartItem.item, eventHandler = eventHandler)
                }
                Spacer(modifier = Modifier.height(bottomBarHeight))
            }
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth(1.0f).padding(Dims.smPad).height(bottomBarHeight)) {
                Button(
                    onClick = { eventHandler(NewEditEvent.CancelEvent) },
                    modifier = Modifier.weight(1.0f).padding(Dims.smPad)) { Text(text = "Cancel") }
                Button(
                    onClick = { eventHandler(NewEditEvent.DoneEvent) },
                    modifier = Modifier.weight(1.0f).padding(Dims.smPad)) { Text(text = "Done") }
            }
        }
    )
}

@Composable
private fun ShakeSection(shake: Shake, eventHandler: (NewEditEvent) -> Unit) {
    ShakeDetails(
        shake = shake,
        onVanillaCheckChanged = { vanilla ->
            val newShake = shake.copy(containsVanilla = vanilla)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onChocolateCheckChanged = { chocolate ->
            val newShake = shake.copy(containsChocolate = chocolate)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onStrawberryCheckChanged = { strawberry ->
            val newShake = shake.copy(containsStrawberry = strawberry)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onCupsChanged = { cups ->
            val newShake = shake.copy(splitIntoCups = cups)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onSizeSelect = { shakeSize ->
            val newShake = shake.copy(size = shakeSize)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newShake))
        }
    )
}

@Composable
private fun SoftDrinkSection(softDrink: SoftDrink, eventHandler: (NewEditEvent) -> Unit) {
    Column {
        SoftDrinkFlavor(
            flavor = softDrink.type,
            onFlavorChanged = { newFlavor ->
                val newSoda = softDrink.copy(type = newFlavor)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
        SoftDrinkSize(
            size = softDrink.size,
            onSizeSelect = { size ->
                val newSoda = softDrink.copy(size = size)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
    }
}

@Composable
private fun FloatSection(float: FloatDrink, eventHandler: (NewEditEvent) -> Unit) {
    Column {
        SoftDrinkFlavor(
            flavor = float.softDrinkType,
            onFlavorChanged = { newFlavor ->
                val newSoda = float.copy(softDrinkType = newFlavor)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
        ShakeDetails(
            shake = float.shake,
            onVanillaCheckChanged = { vanilla ->
                val newFloat = float.copy(shake = float.shake.copy(containsVanilla = vanilla))
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onChocolateCheckChanged = { chocolate ->
                val newFloat = float.copy(shake = float.shake.copy(containsChocolate = chocolate))
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onStrawberryCheckChanged = { strawberry ->
                val newFloat = float.copy(shake = float.shake.copy(containsStrawberry = strawberry))
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onCupsChanged = { cups ->
                val newFloat = float.copy(shake = float.shake.copy(splitIntoCups = cups))
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onSizeSelect = { shakeSize ->
                val newFloat = float.copy(shake = float.shake.copy(size = shakeSize))
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            }
        )
    }
}

@Composable
private fun HamburgerSection(hamburger: Hamburger, eventHandler: (NewEditEvent) -> Unit) {
    Column {
        QuantitySelector(
            titleString = "Patties", // TODO Resource String
            currentQuantity = hamburger.patties,
            onQuantityChanged = { newPattiesCount ->
                val newBurger = hamburger.copy(patties = newPattiesCount)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            },
        )
        QuantitySelector(
            titleString = "Cheese Slices", // TODO Resource String
            currentQuantity = hamburger.slices,
            onQuantityChanged = { newSlicesCount ->
                val newBurger = hamburger.copy(slices = newSlicesCount)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            },
        )
        BooleanPicker(
            // TODO Resource Strings
            sectionName = "Patty Style",
            trueUiString = "Mustard Fried",
            falseUiString = "Regular",
            trueDesc = "Patties are cooked with mustard. Standard option for Animal Style. It's good!",
            value = hamburger.mustardFried,
            onSelect = { mf ->
                val newBurger = hamburger.copy(mustardFried = mf)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            }
        )
        BooleanPicker(
            // TODO Resource Strings
            sectionName = "Done-ness",
            trueUiString = "Extra Well Done",
            falseUiString = "Well Done",
            trueDesc = "Patties are cooked extra long. I have never seen anyone do this?",
            value = hamburger.extraWellDone,
            onSelect = { ewd ->
                val newBurger = hamburger.copy(extraWellDone = ewd)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            }
        )
        MultiPicker(
            sectionName = "Buns",
            options = Buns.entries.map {
                MultiPickerOption(
                    value = it,
                    uiString = it.uiString
                )
            },
            selected = MultiPickerOption(
                value = hamburger.buns,
                uiString = hamburger.buns.uiString
            ),
            onSelect = { bun ->
                val newBurger = hamburger.copy(buns = bun)
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            }
        )
        hamburger.condiments.map { c ->
            CondimentPicker(
                condiment = c,
                onCondimentLevelChanged = { condimentLevel: CondimentLevel ->
                    val newBurger = hamburger.copy(
                        condiments = hamburger.condiments.map { condiment ->
                            if (condiment.condimentType == c.condimentType) {
                                condiment.copy(level = condimentLevel)
                            } else {
                                condiment
                            }
                        }
                    )
                    eventHandler(NewEditEvent.UpdateCurrentItemEvent(newBurger))
                }
            )
        }
    }
}

@Composable
private fun FrySection(frenchFries: FrenchFries, eventHandler: (NewEditEvent) -> Unit) {
    MultiPicker(
        sectionName = "Done-ness", // TODO Resource string
        options = FryCookedLevels.entries.map { level ->
            MultiPickerOption(
                value = level,
                uiString = level.uiString,
                description = level.warning
            )
        },
        selected = MultiPickerOption(
            value = frenchFries.cookedLevels,
            uiString = frenchFries.cookedLevels.uiString,
            description = frenchFries.cookedLevels.warning
        ),
        onSelect = { level: FryCookedLevels ->
            val newFries = frenchFries.copy(cookedLevels = level)
            eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFries))
        }
    )
    frenchFries.condiments.map { c ->
        CondimentPicker(
            condiment = c,
            onCondimentLevelChanged = { condimentLevel: CondimentLevel ->
                val newFries = frenchFries.copy(
                    condiments = frenchFries.condiments.map { condiment ->
                        if (condiment.condimentType == c.condimentType) {
                            condiment.copy(level = condimentLevel)
                        } else {
                            condiment
                        }
                    }
                )
                eventHandler(NewEditEvent.UpdateCurrentItemEvent(newFries))
            }
        )
    }
}


@Composable
private fun CondimentPicker(condiment: Condiment, onCondimentLevelChanged: (CondimentLevel) -> Unit) {
    MultiPicker(
        sectionName = condiment.condimentType.uiString,
        options = CondimentLevel.entries.map {
            MultiPickerOption(
                value = it,
                uiString = it.uiString
            )
        },
        selected = MultiPickerOption(
            value = condiment.level,
            uiString = condiment.level.uiString
        ),
        onSelect = onCondimentLevelChanged
    )
}

@Composable
private fun SoftDrinkFlavor(flavor: SoftDrinkType, onFlavorChanged: (SoftDrinkType) -> Unit) {
    MultiPicker(
        sectionName = "Flavor", // TODO resource string
        options = SoftDrinkType.entries.map {
            MultiPickerOption(
                value = it,
                uiString = it.uiString,
                description = it.description
            )
        },
        selected = MultiPickerOption(
            value = flavor,
            uiString = flavor.uiString,
            description = flavor.description
        ),
        onSelect = onFlavorChanged
    )
}

@Composable
private fun SoftDrinkSize(size: SoftDrinkSize, onSizeSelect: (SoftDrinkSize) -> Unit) {
    MultiPicker(
        sectionName = "Size", // TODO resource string
        options = SoftDrinkSize.entries.map {
            MultiPickerOption(
                value = it,
                uiString = it.uiString,
                description = it.description
            )
        },
        selected = MultiPickerOption(
            value = size,
            uiString = size.uiString,
            description = size.description
        ),
        onSelect = onSizeSelect
    )
}

@Composable
private fun ShakeDetails(
    shake: Shake,
    onVanillaCheckChanged: (Boolean) -> Unit,
    onStrawberryCheckChanged: (Boolean) -> Unit,
    onChocolateCheckChanged: (Boolean) -> Unit,
    onSizeSelect: (ShakeSize) -> Unit,
    onCupsChanged: (Int) -> Unit
) {
    Column {
        Row {
            Column {
                Checkbox(
                    checked = shake.containsStrawberry,
                    onCheckedChange = onStrawberryCheckChanged
                )
                Text(text = "Strawberry")
            }
            Column {
                Checkbox(
                    checked = shake.containsVanilla,
                    onCheckedChange = onVanillaCheckChanged
                )
                Text(text = "Vanilla")
            }
            Column {
                Checkbox(
                    checked = shake.containsChocolate,
                    onCheckedChange = onChocolateCheckChanged
                )
                Text(text = "Chocolate")
            }
        }
        MultiPicker(
            sectionName = "Size", // TODO Resource String
            options = ShakeSize.entries.map { size ->
                MultiPickerOption(
                    value = size,
                    uiString = size.uiString
                )
            },
            selected = MultiPickerOption(
                value = shake.size,
                uiString = shake.size.uiString
            ),
            onSelect = onSizeSelect
        )
        QuantitySelector(
            titleString = "Split into cups",
            onQuantityChanged = onCupsChanged,
            currentQuantity = shake.splitIntoCups
        )
    }
}

@Composable
fun ItemDetailsLoading() {
    // TODO This could be a spinner or something
    Text(text = "Loading")
}

@Composable
fun ItemDetailsError() {
    Text("Oh no!")
}
