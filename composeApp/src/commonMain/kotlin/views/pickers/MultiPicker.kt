package views.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
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


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> MultiPicker(
    sectionName: String,
    options: List<MultiPickerOption<T>>,
    selected: MultiPickerOption<T>,
    onSelect: (T) -> Unit
) {
    Column {
        Text(text = sectionName, style = MaterialTheme.typography.h3)
        if (options.size <= 6) {
            Column {
                Row {
                    options.map { option ->
                        val buttonBackground = if (option.uiString == selected.uiString) {
                            MaterialTheme.colors.primary
                        } else {
                            MaterialTheme.colors.surface
                        }
                        Button(onClick = { onSelect(option.value) }, modifier = Modifier.background(buttonBackground)) {
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
                    label = { Text("Label") },
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
