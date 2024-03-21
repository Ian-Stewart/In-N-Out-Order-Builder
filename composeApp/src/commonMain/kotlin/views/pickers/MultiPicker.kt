package views.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constants.Dims


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> MultiPicker(
    sectionName: String,
    options: List<MultiPickerOption<T>>,
    selected: MultiPickerOption<T>,
    onSelect: (T) -> Unit
) {
    val buttonText = MaterialTheme.typography.button
    val sectionNameText = MaterialTheme.typography.h4
    val startPadValues = PaddingValues(start = 0.dp, end = Dims.smPad, top = Dims.smPad, bottom = Dims.smPad)
    val endPadValues = PaddingValues(start = Dims.smPad, end = 0.dp, top = Dims.smPad, bottom = Dims.smPad)
    val selectedColor = MaterialTheme.colors.primaryVariant
    val deselectedColor = MaterialTheme.colors.primary

    Column(modifier = Modifier.fillMaxWidth().padding(Dims.smPad)) {
        Text(text = sectionName, style = sectionNameText)
        if (options.size <= 3) {
            Column {
                Row(modifier = Modifier.padding(Dims.smPad), horizontalArrangement = Arrangement.SpaceEvenly) {
                    options.mapIndexed { index, option ->
                        val buttonBackground = if (option.uiString == selected.uiString) { selectedColor } else { deselectedColor }
                        val paddingValues = when (index) {
                            0 -> PaddingValues(start = 0.dp, end = Dims.smPad, top = Dims.smPad, bottom = Dims.smPad)
                            options.lastIndex -> PaddingValues(start = Dims.smPad, end = 0.dp, top = Dims.smPad, bottom = Dims.smPad)
                            else -> PaddingValues(Dims.smPad)
                        }
                        Button(
                            onClick = { onSelect(option.value) },
                            modifier = Modifier.padding(paddingValues),
                            colors = ButtonDefaults.buttonColors(backgroundColor = buttonBackground)
                        ) {
                            Text(text = option.uiString, style = MaterialTheme.typography.button)
                        }
                    }
                }
                selected.description?.let { desc ->
                    Text(text = desc, style = MaterialTheme.typography.caption)
                }
            }
        } else {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selected.uiString,
                    onValueChange = { },
                    label = {  },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                onSelect( selectionOption.value )
                                expanded = false
                            }
                        ){
                            Text(text = selectionOption.uiString)
                        }
                    }
                }
            }
        }
    }
}

data class MultiPickerOption<T>(
    val value: T,
    val uiString: String,
    val description: String? = null
)
