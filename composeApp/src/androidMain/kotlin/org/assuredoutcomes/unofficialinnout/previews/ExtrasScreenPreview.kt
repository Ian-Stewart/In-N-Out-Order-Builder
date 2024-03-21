package org.assuredoutcomes.unofficialinnout.previews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import views.screens.ExtrasDetail

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun ExtrasPreview() {
    MaterialTheme {
        ExtrasDetail(
            spreadPackets = 1,
            pupPatties = 1,
            pepperPackets = 1,
            onAdjustPepperQuantity = { _ -> },
            onAdjustPupPattyQuantity = { _ -> },
            onAdjustSpreadQuantity = { _ -> }
        )
    }
}