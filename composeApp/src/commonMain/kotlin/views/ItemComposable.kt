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
import viewmodel.NewEditEvent
import viewmodel.NewEditViewModel

@Composable
fun ItemComposable(newEditViewModel: NewEditViewModel) {
    val state = newEditViewModel.stateFlow.collectAsState()
    val cartItem = state.value.item
    if (cartItem == null || state.value.error) {
        Text(text = "Unknown Error!")
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
            // Options

            // Quantity
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
    }
}