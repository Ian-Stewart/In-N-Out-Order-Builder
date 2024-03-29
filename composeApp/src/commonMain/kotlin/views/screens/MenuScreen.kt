package views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import constants.Dims
import constants.ImagePath
import menuitems.MenuItemType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import utils.MenuItemImage
import viewmodel.MenuEvent
import viewmodel.MenuViewModel

class MenuScreen: Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val menuViewModel: MenuViewModel = koinInject()
        menuViewModel.bindOnExtrasClick { navigator.push(ExtrasScreen()) }
        menuViewModel.bindOnNewItemClick { itemType -> navigator.push(NewItemScreen(itemType)) }
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Menu", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h1, color = MaterialTheme.colors.onSurface)
            MenuItemType.entries.map { type ->
                val image = painterResource(MenuItemImage.headerImageForType(type))
                val category = titleForType(type)
                ItemCard(
                    backgroundImage = image,
                    title = category,
                    contentDescription = "Header image for ${type.name}",
                    onClick = { menuViewModel.onEvent(MenuEvent.NewItemEvent(type)) }
                )
            }
            ItemCard(
                backgroundImage = painterResource(ImagePath.EXTRAS_HEADER.path),
                title = "Extras",
                contentDescription = "Header image for extras",
                onClick = { menuViewModel.onEvent(MenuEvent.ExtrasEvent) }
            )
        }
    }

    @Composable
    fun ItemCard(
        backgroundImage: Painter,
        title: String,
        contentDescription: String,
        onClick: () -> Unit
    ) {
        Box(modifier = Modifier.padding(Dims.smPad).clickable { onClick() }) {
            Card(modifier = Modifier.fillMaxWidth().shadow(Dims.shadowHeight)) {
                Column {
                    Image(
                        painter = backgroundImage,
                        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                        contentDescription = contentDescription
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(Dims.smPad)
                    )
                }
            }
        }
    }

    private fun titleForType(menuItemType: MenuItemType): String {
        // TODO how do I pull these from resources?
        return when (menuItemType) {
            MenuItemType.BURGER -> "Burgers"
            MenuItemType.SHAKE -> "Shakes"
            MenuItemType.SODY_POP -> "Sodas/Drinks"
            MenuItemType.FLOAT -> "Floats"
            MenuItemType.FRIES -> "French Fries"
        }
    }
}