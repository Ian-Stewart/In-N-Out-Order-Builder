package views.pickers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import constants.Dims

@Composable
fun QuantitySelector(
    titleString: String,
    currentQuantity: Int,
    onQuantityChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Quantity
    Column(modifier = modifier.padding(Dims.smPad)) {
        Text(text = titleString, style = MaterialTheme.typography.h4)
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            val startPaddingValues = PaddingValues(start = 0.dp, end = Dims.smPad, top = Dims.smPad, bottom = Dims.smPad)
            Button(
                modifier = Modifier.height(Dims.rowHeight).padding(paddingValues = startPaddingValues),
                onClick = { onQuantityChanged(currentQuantity.minus(1)) }
            ) {
                Text(text = "-", style = MaterialTheme.typography.h4)
            }
            TextField(
                value = currentQuantity.toString(),
                modifier = Modifier
                    .weight(0.5f)
                    .height(Dims.rowHeight)
                    .padding(Dims.smPad)
                ,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                textStyle = MaterialTheme.typography.h6,
                onValueChange = { string -> string.toIntOrNull()?.let{ onQuantityChanged(it) } }
            )
            val endPadValues = PaddingValues(start = Dims.smPad, end = 0.dp, top = Dims.smPad, bottom = Dims.smPad)

            Button(
                modifier = Modifier.height(Dims.rowHeight).padding(paddingValues = endPadValues),
                onClick = { onQuantityChanged(currentQuantity.plus(1)) }
            ) { Text(text = "+", style = MaterialTheme.typography.h4)
            }
        }
    }
}