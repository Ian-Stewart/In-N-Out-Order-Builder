package org.assuredoutcomes.unofficialinnout.previews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import viewmodel.OrderItem
import views.tabs.ItemRow

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun ItemRowPreview() {
    MaterialTheme {
        ItemRow(OrderItem(
            count = 5,
            name = "Test test Test test Test test Test test Test test Test test Test test Test test Test test Test test Test test Test test Test test Test test "
        ))
    }
}