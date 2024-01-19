package tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import constants.ImagePath
import menuitems.MenuItemType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

object MenuTab : Tab {
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

    @Composable
    override fun Content() {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Text(text = "Menu", modifier = Modifier.fillMaxWidth(), style = MaterialTheme.typography.h1, color = MaterialTheme.colors.onSurface)
            MenuItemType.entries.map { ItemCard(it) }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ItemCard(menuItemType: MenuItemType) {
    Box(modifier = Modifier.padding(4.dp)) {
        Card(modifier = Modifier.fillMaxWidth().shadow(2.dp)) {
            Column {
                val image = painterResource(headerImageForType(menuItemType))
                val category = titleForType(menuItemType)
                Image(
                    painter = image,
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                    contentDescription = "Header image for ${menuItemType.name}"
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(4.dp)
                )
            }
        }
    }
}

private fun headerImageForType(menuItemType: MenuItemType): String {
    return when (menuItemType) {
        MenuItemType.BURGER -> ImagePath.BURGER_HEADER.path
        MenuItemType.SHAKE -> ImagePath.SHAKE_HEADER.path
        MenuItemType.SODY_POP -> ImagePath.DRINK_HEADER.path
        MenuItemType.FLOAT -> ImagePath.FLOAT_HEADER.path
        MenuItemType.FRIES -> ImagePath.FRIES_HEADER.path
        MenuItemType.EXTRAS -> ImagePath.EXTRAS_HEADER.path
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
        MenuItemType.EXTRAS -> "Extras"
    }
}