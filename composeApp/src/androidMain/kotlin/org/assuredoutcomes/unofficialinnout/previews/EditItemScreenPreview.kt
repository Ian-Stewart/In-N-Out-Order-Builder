package org.assuredoutcomes.unofficialinnout.previews

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import repo.TemporaryFakeCart
import views.ItemDetail

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun ItemDetailsPreview() {
    MaterialTheme {
        ItemDetail(
            cartItem = TemporaryFakeCart.cart.cartItems.first(),
            isNewItem = true,
            eventHandler = {})
    }
}