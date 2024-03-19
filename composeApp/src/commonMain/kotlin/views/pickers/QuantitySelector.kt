package views.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun QuantitySelector(
    titleString: String,
    currentQuantity: Int,
    onQuantityChanged: (Int) -> Unit
) {
    // Quantity
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = titleString)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { onQuantityChanged(currentQuantity.minus(1)) }) { Text(text = "<") }
            TextField(
                value = currentQuantity.toString(),
                modifier = Modifier.weight(0.5f),
                onValueChange = { string -> string.toIntOrNull()?.let{ onQuantityChanged(it) } }
            )
            Button(onClick = { onQuantityChanged(currentQuantity.plus(1)) }) { Text(text = ">") }
        }
    }
}