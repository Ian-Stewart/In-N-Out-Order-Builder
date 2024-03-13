package views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import menuitems.Condiment
import menuitems.CondimentLevel
import menuitems.FloatDrink
import menuitems.FrenchFries
import menuitems.Hamburger
import menuitems.Item
import menuitems.Shake
import menuitems.SoftDrink
import menuitems.SoftDrinkType
import viewmodel.NewEditEvent
import viewmodel.NewEditViewModel

@Composable
fun ItemComposable(newEditViewModel: NewEditViewModel) {
    val state = newEditViewModel.stateFlow.collectAsState()
    val cartItem = state.value.item
    if (cartItem == null || state.value.error) {
        NullItemDetails()
    } else {
        Column {
            Text(
                text = if (state.value.newItem) { "Add" } else { "Edit" },
                style = MaterialTheme.typography.h2
            )
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { newEditViewModel.onEvent(NewEditEvent.DoneEvent) }) {
                    Text(text = "Done")
                }
                Button(onClick = { newEditViewModel.onEvent(NewEditEvent.CancelEvent) }) {
                    Text(text = "Cancel")
                }
            }
            // Name
            Text(
                text = cartItem.item.itemName()
            )

            // Quantity
            Column {
                Text(text = "Quantity")
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(onClick = {
                        newEditViewModel.onEvent(NewEditEvent.QuantityEvent(cartItem.quantity.minus(1)))
                    }) {
                        Text(text = "<")
                    }
                    TextField(value = cartItem.quantity.toString(), onValueChange = { string ->
                        NewEditEvent.QuantityEvent(string.toIntOrNull() ?: cartItem.quantity)
                    })
                    Button(onClick = {
                        newEditViewModel.onEvent(NewEditEvent.QuantityEvent(cartItem.quantity.plus(1)))
                    }) {
                        Text(text = ">")
                    }
                }
            }

            when (cartItem.item) {
                is Hamburger -> {
                    HamburgerSection(hamburger = cartItem.item, viewModel = newEditViewModel)
                    FoodToppings(condiments = cartItem.item.condiments, viewModel = newEditViewModel)
                }
                is FrenchFries -> {
                    FrySection(frenchFries = cartItem.item, viewModel = newEditViewModel)
                    FoodToppings(condiments = cartItem.item.condiments, viewModel = newEditViewModel)
                }
                is FloatDrink -> {
                    SoftDrinkFlavor()
                    ShakeSection()
                }
                is SoftDrink -> {
                    SoftDrinkFlavor()
                    SoftDrinkSize()
                }
                is Shake -> {
                    ShakeSection()
                }
            }
        }
    }
}

@Composable
private fun HamburgerSection(hamburger: Hamburger, viewModel: NewEditViewModel) {

}

@Composable
private fun FrySection(frenchFries: FrenchFries, viewModel: NewEditViewModel) {

}

@Composable
private fun Condiment(condiment: Condiment, viewModel: NewEditViewModel) {
    Column {
        Text(text = condiment.condimentType.uiString, style = MaterialTheme.typography.body1)
        Row(modifier = Modifier.fillMaxWidth()) {
            CondimentLevel.entries.map { level ->
                Button(onClick = ) {
                    Text(level.uiString)
                }
            }
        }
        if (condiment.condimentType.description != null) {
            Text(text = condiment.condimentType.description, style = MaterialTheme.typography.subtitle1)
        }
    }
}

@Composable
private fun SoftDrinkFlavor() {

}

@Composable
private fun SoftDrinkSize() {

}

@Composable
private fun ShakeSection() {

}

@Composable
private fun FoodToppings(condiments: List<Condiment>, viewModel: NewEditViewModel) {
    condiments.forEach { condiment ->
        Column {
            Text(text = condiment.condimentType.uiString)
        }
    }
}

@Composable
private fun NullItemDetails() {
    Text(text = "Item is null")
}