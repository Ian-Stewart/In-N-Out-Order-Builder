import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.navigator.tab.Tab
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import constants.Dimens
import repo.CartRepository
import tabs.CartTab
import tabs.MenuTab
import tabs.ReadyTab
import viewmodel.CartViewModel
import viewmodel.ExtrasViewModel
import viewmodel.MenuViewModel
import viewmodel.NewEditEvent
import viewmodel.NewEditViewModel
import viewmodel.OrderViewModel
import views.ExtrasComposable
import views.ItemComposable

@Composable
fun App() {
    // TODO set this up properly, just being a little lazy here tbh
    val repository = remember { CartRepository() }

    var isEditingExtras by remember { mutableStateOf(false) }
    var isEditingItem by remember { mutableStateOf(false) }

    val extrasViewModel = remember { ExtrasViewModel(repository, { isEditingExtras = false }) }
    val newEditViewModel = remember { NewEditViewModel(
        cartRepository = repository,
        onNewOrEdit = { isEditingItem = true },
        onDone = { isEditingItem = false }
    ) }
    val orderViewModel = remember { OrderViewModel(repository) }
    val cartViewModel = remember { CartViewModel(
        cartRepository = repository,
        onEditExtras = { isEditingExtras = true },
        onEditItem = { cartItemUUID ->
            newEditViewModel.onEvent(NewEditEvent.EditItemEvent(cartItemUUID))
        }
    ) }
    val menuViewModel = remember { MenuViewModel(
        onExtrasClick = { isEditingExtras = true },
        onNewItemClick = { itemType -> newEditViewModel.onEvent(NewEditEvent.NewItemEvent(itemType)) }
    ) }

    val menuTab = remember {
        MenuTab(
            menuViewModel = menuViewModel,
            onEditExtras = { isEditingExtras = true }
        )
    }
    val cartTab = remember { CartTab(cartViewModel) }
    val readyTab = remember { ReadyTab(orderViewModel) }
    MaterialTheme {
        if (isEditingExtras) {
            ExtrasComposable(extrasViewModel)
        } else if (isEditingItem) {
            ItemComposable(newEditViewModel)
        } else {
            TabNavigator(menuTab) {
                Scaffold(
                    content = {
                        Column(modifier = Modifier.padding(bottom = Dimens.tabHeight)) { CurrentTab() }
                    },
                    bottomBar = {
                        BottomNavigation(modifier = Modifier.height(Dimens.tabHeight)) {
                            TabNavigationItem(menuTab)
                            TabNavigationItem(cartTab)
                            TabNavigationItem(readyTab)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        label = { Text(text = tab.options.title) },
        icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) } // TODO remove double bangs
    )
}