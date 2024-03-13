package tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import constants.Dimens
import constants.ImagePath
import menuitems.MenuItemType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.MenuItemImage.Companion.headerImageForType
import viewmodel.MenuEvent
import viewmodel.MenuViewModel

class MenuTab(
    private val menuViewModel: MenuViewModel,
    private val onEditExtras: () -> Unit
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Menu"
            val icon = rememberVectorPainter(Icons.Default.Add)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Menu", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h1, color = MaterialTheme.colors.onSurface)
            MenuItemType.entries.map { type ->
                val image = painterResource(headerImageForType(type))
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
                onClick = onEditExtras
            )
        }
    }
}

@Composable
fun ItemCard(
    backgroundImage: Painter,
    title: String,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.padding(Dimens.smallPadding).clickable { onClick() }) {
        Card(modifier = Modifier.fillMaxWidth().shadow(Dimens.shadowHeight)) {
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
                    modifier = Modifier.fillMaxWidth().padding(Dimens.smallPadding)
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