package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
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
import org.koin.compose.koinInject
import viewmodel.NewEditEvent
import viewmodel.NewEditViewModel
import views.pickers.BooleanPicker
import views.pickers.MultiPicker
import views.pickers.MultiPickerOption
import views.pickers.QuantitySelector

/**
 * Composable to edit items
 * TODO this should be broken down into sub-composables and have some stuff moved into the viewmodel
 * However, again, I am mostly trying to get something that works in compose multiplatform, not be like,
 * good or maintainable or whatever. Make of that what you will!
 */
@Composable
fun ItemComposable(
    onDone: () -> Unit,
    newEditViewModel: NewEditViewModel = koinInject()
) {
    newEditViewModel.bindOnDone(onDone)
    newEditViewModel.bindOnNewOrEdit(onNewOrEdit)

    val state = newEditViewModel.stateFlow.collectAsState()
    val cartItem = state.value.item
    if (cartItem == null || state.value.error) {
        NullItemDetails()
    } else {
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.fillMaxHeight().verticalScroll(scrollState), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = if (state.value.newItem) { "Add" } else { "Edit" },
                style = MaterialTheme.typography.h2
            )
            // Name
            Text(text = cartItem.item.itemName())

            QuantitySelector(
                titleString = "Quantity",
                currentQuantity = cartItem.quantity,
                onQuantityChanged = { quantity: Int -> newEditViewModel.onEvent(NewEditEvent.QuantityEvent(quantity)) }
            )

            when (cartItem.item) {
                is Hamburger -> {
                    HamburgerSection(hamburger = cartItem.item, viewModel = newEditViewModel)
                }
                is FrenchFries -> {
                    FrySection(frenchFries = cartItem.item, viewModel = newEditViewModel)
                }
                is FloatDrink -> {
                    FloatSection(float = cartItem.item, viewModel = newEditViewModel)
                }
                is SoftDrink -> {
                    SoftDrinkSection(softDrink = cartItem.item, viewModel = newEditViewModel)
                }
                is Shake -> {
                    ShakeSection(shake = cartItem.item, viewModel = newEditViewModel)
                }
            }
            Row(modifier = Modifier.fillMaxWidth().weight(1f, false)) {
                Button(onClick = { newEditViewModel.onEvent(NewEditEvent.DoneEvent) }) {
                    Text(text = "Done")
                }
                Button(onClick = { newEditViewModel.onEvent(NewEditEvent.CancelEvent) }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}

@Composable
private fun ShakeSection(shake: Shake, viewModel: NewEditViewModel) {
    ShakeDetails(
        shake = shake,
        onVanillaCheckChanged = { vanilla ->
            val newShake = shake.copy(containsVanilla = vanilla)
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onChocolateCheckChanged = { chocolate ->
            val newShake = shake.copy(containsChocolate = chocolate)
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onStrawberryCheckChanged = { strawberry ->
            val newShake = shake.copy(containsStrawberry = strawberry)
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onCupsChanged = { cups ->
            val newShake = shake.copy(splitIntoCups = cups)
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newShake))
        },
        onSizeSelect = { shakeSize ->
            val newShake = shake.copy(size = shakeSize)
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newShake))
        }
    )
}

@Composable
private fun SoftDrinkSection(softDrink: SoftDrink, viewModel: NewEditViewModel) {
    Column {
        SoftDrinkFlavor(
            flavor = softDrink.type,
            onFlavorChanged = { newFlavor ->
                val newSoda = softDrink.copy(type = newFlavor)
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
        SoftDrinkSize(
            size = softDrink.size,
            onSizeSelect = { size ->
                val newSoda = softDrink.copy(size = size)
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
    }
}

@Composable
private fun FloatSection(float: FloatDrink, viewModel: NewEditViewModel) {
    Column {
        SoftDrinkFlavor(
            flavor = float.softDrinkType,
            onFlavorChanged = { newFlavor ->
                val newSoda = float.copy(softDrinkType = newFlavor)
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newSoda))
            }
        )
        ShakeDetails(
            shake = float.shake,
            onVanillaCheckChanged = { vanilla ->
                val newFloat = float.copy(shake = float.shake.copy(containsVanilla = vanilla))
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onChocolateCheckChanged = { chocolate ->
                val newFloat = float.copy(shake = float.shake.copy(containsChocolate = chocolate))
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onStrawberryCheckChanged = { strawberry ->
                val newFloat = float.copy(shake = float.shake.copy(containsStrawberry = strawberry))
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onCupsChanged = { cups ->
                val newFloat = float.copy(shake = float.shake.copy(splitIntoCups = cups))
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            },
            onSizeSelect = { shakeSize ->
                val newFloat = float.copy(shake = float.shake.copy(size = shakeSize))
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFloat))
            }
        )
    }
}

@Composable
private fun HamburgerSection(hamburger: Hamburger, viewModel: NewEditViewModel) {
    Column {
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
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            }
        )
        QuantitySelector(
            titleString = "Patties", // TODO Resource String
            currentQuantity = hamburger.patties,
            onQuantityChanged = { newPattiesCount ->
                val newBurger = hamburger.copy(patties = newPattiesCount)
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
            },
        )
        QuantitySelector(
            titleString = "Cheese Slices", // TODO Resource String
            currentQuantity = hamburger.slices,
            onQuantityChanged = { newSlicesCount ->
                val newBurger = hamburger.copy(slices = newSlicesCount)
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
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
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
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
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
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
                    viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newBurger))
                }
            )
        }
    }
}

@Composable
private fun FrySection(frenchFries: FrenchFries, viewModel: NewEditViewModel) {
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
            viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFries))
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
                viewModel.onEvent(NewEditEvent.UpdateCurrentItemEvent(newFries))
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
private fun NullItemDetails() {
    Text(text = "Item is null")
}