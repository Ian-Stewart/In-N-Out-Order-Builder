package views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import constants.Dimens
import menuitems.Extra
import viewmodel.ExtrasEvent
import viewmodel.ExtrasViewModel

@Composable
fun ExtrasComposable(viewModel: ExtrasViewModel) {
    val state = viewModel.stateFlow.collectAsState()
    Column {
        Text(text = "Extras")
        ExtrasDetail(
            pepperPackets = state.value.pepperPackets,
            pupPatties = state.value.pupPatties,
            spreadPackets = state.value.spreadPackets,
            onAdjustSpreadQuantity = { extraSpread: Int ->
                viewModel.onEvent(ExtrasEvent.SetExtraSpreadEvent(extraSpread))
            },
            onAdjustPupPattyQuantity = { pupPatties: Int ->
                viewModel.onEvent(ExtrasEvent.SetPupPattiesEvent(pupPatties))
            },
            onAdjustPepperQuantity = { pepperPackets: Int ->
                viewModel.onEvent(ExtrasEvent.SetPepperPacketsEvent(pepperPackets))
            }
        )
        Button(onClick = { viewModel.onEvent(ExtrasEvent.CloseViewEvent) }) {
            Text(text = "Done")
        }
    }
}

@Composable
fun ExtrasDetail(
    pepperPackets: Int,
    pupPatties: Int,
    spreadPackets: Int,
    onAdjustPepperQuantity: (Int) -> Unit,
    onAdjustPupPattyQuantity: (Int) -> Unit,
    onAdjustSpreadQuantity: (Int) -> Unit
) {
    Column {
        ExtraRow(
            name = Extra.PEPPER_PACKET.itemName(),
            quantity = pepperPackets,
            onAdjustQuantity = onAdjustPepperQuantity
        )
        ExtraRow(
            name = Extra.PUP_PATTY.itemName(),
            quantity = pupPatties,
            onAdjustQuantity = onAdjustPupPattyQuantity
        )
        ExtraRow(
            name = Extra.SPREAD_PACKET.itemName(),
            quantity = spreadPackets,
            onAdjustQuantity = onAdjustSpreadQuantity
        )
    }
}

@Composable
fun ExtraRow(name: String, quantity: Int, onAdjustQuantity: (Int) -> Unit) {
    Column(modifier = Modifier.padding(Dimens.smallPadding)) {
        Text(text = name)
        Row {
            Button(onClick = {
                onAdjustQuantity(quantity.minus(1))
            }) {
                Text(text = " < ")
            }
            Text(text = quantity.toString())
            Button(onClick = {
                onAdjustQuantity(quantity.plus(1))
            }) {
                Text(text = " > ")
            }
        }
    }
}

@Composable
fun ExtrasPreview() {
    ExtrasDetail(
        spreadPackets = 1,
        pupPatties = 1,
        pepperPackets = 1,
        onAdjustPepperQuantity = { _ -> },
        onAdjustPupPattyQuantity = { _ -> },
        onAdjustSpreadQuantity = { _ -> }
    )
}

@Composable
fun PreviewRow() {
    ExtraRow(
        name = Extra.SPREAD_PACKET.itemName(),
        quantity = 5,
        onAdjustQuantity = { _ -> }
    )
}