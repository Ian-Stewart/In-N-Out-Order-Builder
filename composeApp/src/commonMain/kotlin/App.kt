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
import menuitems.MenuItemType
import org.koin.compose.koinInject
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
    var isEditingExtras by remember { mutableStateOf(false) }
    var isEditingItem by remember { mutableStateOf(false) }

    val onDoneEditingExtras: () -> Unit = { isEditingExtras = false }
    val onStopEditingItem: () -> Unit = { isEditingItem = false }
    //val onEditCartItem: (String) -> Unit = { cartItemUUID -> newEditViewModel.onEvent(NewEditEvent.EditExistingItemEvent(cartItemUUID)) }
    //val onNewItem: (MenuItemType) -> Unit = { itemType -> newEditViewModel.onEvent(NewEditEvent.CreateNewItemEvent(itemType)) }

    val menuTab = MenuTab(onEditExtras = { isEditingExtras = true })
    val cartTab = CartTab()
    val readyTab = ReadyTab()
    MaterialTheme {
        if (isEditingExtras) {
            ExtrasComposable(onDoneEditingExtras = onDoneEditingExtras)
        } else if (isEditingItem) {
            ItemComposable(onDone = onStopEditingItem)
        } else {
            TabNavigator(menuTab) {
                Scaffold(
                    content = { Column(
                        modifier = Modifier.padding(bottom = Dimens.tabHeight)) {
                            CurrentTab()
                        }
                    },
                    bottomBar = { BottomNavigation(
                        modifier = Modifier.height(Dimens.tabHeight)) {
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