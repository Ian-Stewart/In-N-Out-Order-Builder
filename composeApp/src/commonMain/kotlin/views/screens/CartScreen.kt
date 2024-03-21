package views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import constants.Dims
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import repo.CartItem
import utils.MenuItemImage
import viewmodel.CartEvent
import viewmodel.CartViewModel

class CartScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: CartViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow

        val state = viewModel.stateFlow.collectAsState()
        val cart = state.value.cart

        viewModel.bindOnEditExtras { navigator.push(ExtrasScreen()) }
        viewModel.bindOnEditItem { id -> navigator.push(EditItemScreen(id)) }

        Column {
            LazyColumn {
                items(items = cart.cartItems) { item ->
                    CartItemCard(item) { cartItemUUID ->
                        viewModel.onEvent(CartEvent.EditItemEvent(cartItemUUID))
                    }
                }
            }
            ExtraRow(
                pepperPackets = cart.pepperPackets,
                spreadPackets = cart.spreadPackets,
                pupPatties = cart.pupPatties,
                onExtrasClick = { viewModel.onEvent(CartEvent.EditExtrasEvent) }
            )
        }
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun CartItemCard(cartItem: CartItem, onEditClick: (String) -> Unit) {
        Card(modifier = Modifier.fillMaxWidth().shadow(Dims.shadowHeight).clickable { onEditClick(cartItem.id) }) {
            Column {
                val icon = painterResource(MenuItemImage.headerImageForType(cartItem.item.menuItemType()))
                val description = cartItem.item.itemName()
                val quantity = cartItem.quantity
                Image(painter = icon, contentDescription = "Icon for item")
                Text(text = quantity.toString())
                Text(text = description, softWrap = true)
            }
        }
    }

    @Composable
    fun ExtraRow(pepperPackets: Int, spreadPackets: Int, pupPatties: Int, onExtrasClick: () -> Unit) {
        Column(modifier = Modifier.fillMaxWidth().clickable { onExtrasClick() }) {
            Text("Extras:")
            Text("Pepper Packets: $pepperPackets")
            Text("Spread Packets: $spreadPackets")
            Text("Pup Patties: $pupPatties")
        }
    }

}

