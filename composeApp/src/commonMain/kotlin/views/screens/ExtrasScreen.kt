package views.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import menuitems.Extra
import org.koin.compose.koinInject
import viewmodel.ExtrasEvent
import viewmodel.ExtrasViewModel
import views.pickers.QuantitySelector

class ExtrasScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel: ExtrasViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        viewModel.bindOnDone { navigator.pop() }
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
        QuantitySelector(
            titleString = Extra.PEPPER_PACKET.itemName(),
            currentQuantity = pepperPackets,
            onQuantityChanged = onAdjustPepperQuantity
        )
        QuantitySelector(
            titleString = Extra.SPREAD_PACKET.itemName(),
            currentQuantity = spreadPackets,
            onQuantityChanged = onAdjustSpreadQuantity
        )
        QuantitySelector(
            titleString = Extra.PUP_PATTY.itemName(),
            currentQuantity = pupPatties,
            onQuantityChanged = onAdjustPupPattyQuantity
        )
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
