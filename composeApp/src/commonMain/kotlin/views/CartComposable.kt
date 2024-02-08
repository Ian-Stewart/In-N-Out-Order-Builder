package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import repo.CartItem
import utils.MenuItemImage
import viewmodel.CartEvent
import viewmodel.CartViewModel

@Composable
fun CartComposable(viewModel: CartViewModel) {
    val state = viewModel.stateFlow.collectAsState()
    val cart = state.value.cart
    Column {
        LazyColumn {
            items(items = cart.cartItems) { item ->
                CartItemCard(item) { cartItemUUID -> viewModel.onEvent(CartEvent.EditItemEvent(cartItemUUID)) }
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
    Card(modifier = Modifier.fillMaxWidth().shadow(4.dp).clickable { onEditClick(cartItem.id) }) {
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
