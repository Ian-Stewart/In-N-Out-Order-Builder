import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import kotlinx.coroutines.FlowPreview
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import repo.CartRepository
import tabs.CartTab
import tabs.MenuTab
import tabs.ReadyTab
import viewmodel.ExtrasViewModel
import viewmodel.NewEditViewModel
import viewmodel.OrderViewModel
import views.ExtrasComposable

@Composable
fun App() {
    // TODO set this up properly
    val repository = remember { CartRepository() }
    val orderViewModel = remember { OrderViewModel(repository) }
    val newEditViewModel = remember { NewEditViewModel(repository) }
    var isEditingExtras by remember { mutableStateOf(false) }
    val extrasViewModel = remember { ExtrasViewModel(repository, { isEditingExtras = false }) }
    val tabHeight = 56.dp
    val menuTab = MenuTab(
        onNewItem = {},
        onEditExtras = { isEditingExtras = true }
    )
    MaterialTheme {
        if (isEditingExtras) {
            ExtrasComposable(extrasViewModel)
        } else {
            TabNavigator(menuTab) {
                Scaffold(
                    content = {
                        Column(modifier = Modifier.padding(bottom = tabHeight)) { CurrentTab() }
                    },
                    bottomBar = {
                        BottomNavigation(modifier = Modifier.height(tabHeight)) {
                            TabNavigationItem(menuTab)
                            TabNavigationItem(CartTab)
                            TabNavigationItem(ReadyTab(orderViewModel))
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
        label = { Text(text = tab.title) },
        icon = { Icon(painter = tab.icon!!, contentDescription = tab.title) } // TODO remove double bangs
    )
}