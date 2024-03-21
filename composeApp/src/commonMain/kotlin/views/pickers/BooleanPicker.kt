package views.pickers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constants.Dims


@Composable
fun BooleanPicker(
    sectionName: String,
    trueUiString: String,
    falseUiString: String,
    trueDesc: String? = null,
    falseDesc: String? = null,
    value: Boolean,
    onSelect: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(Dims.smPad)) {
        Text(text = sectionName, style = MaterialTheme.typography.h4)
        Column {
            Row {
                val startPadValues = PaddingValues(start = 0.dp, end = Dims.smPad, top = Dims.smPad, bottom = Dims.smPad)
                val endPadValues = PaddingValues(start = Dims.smPad, end = 0.dp, top = Dims.smPad, bottom = Dims.smPad)
                val trueBg = if (value) { MaterialTheme.colors.primaryVariant } else { MaterialTheme.colors.primary }
                Button(
                    onClick = { onSelect(true) },
                    modifier = Modifier.padding(paddingValues = startPadValues),
                    colors = ButtonDefaults.buttonColors(backgroundColor = trueBg)
                ) {
                    Text(text = trueUiString, style = MaterialTheme.typography.button)
                }
                val falseBg = if (!value) { MaterialTheme.colors.primaryVariant } else { MaterialTheme.colors.primary }
                Button(
                    onClick = { onSelect(false) },
                    modifier = Modifier.padding(paddingValues = endPadValues),
                    colors = ButtonDefaults.buttonColors(backgroundColor = falseBg)
                ) {
                    Text(text = falseUiString, style = MaterialTheme.typography.button)
                }
            }
            val descriptionText: String? = if (value) { trueDesc } else { falseDesc }
            descriptionText?.let { desc ->
                Text(text = desc, style = MaterialTheme.typography.caption)
            }
        }
    }
}