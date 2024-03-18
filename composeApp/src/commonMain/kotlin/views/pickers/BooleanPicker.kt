package views.pickers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterialApi::class)
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
    Column {
        Text(text = sectionName, style = MaterialTheme.typography.h3)
        Column {
            Row {
                val trueBg = if (value) { MaterialTheme.colors.primary } else { MaterialTheme.colors.surface }
                Button(onClick = { onSelect(true) }, modifier = Modifier.background(trueBg)) {
                    Text(text = trueUiString, style = MaterialTheme.typography.button)
                }
                val falseBg = if (!value) { MaterialTheme.colors.primary } else { MaterialTheme.colors.surface }
                Button(onClick = { onSelect(false) }, modifier = Modifier.background(falseBg)) {
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