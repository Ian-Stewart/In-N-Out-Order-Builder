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
import cafe.adriel.voyager.navigator.tab.Tab
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import constants.Dimens
import views.tabs.CartTab
import views.tabs.MenuTab
import views.tabs.ReadyTab

@Composable
fun App() {
    val menuTab = MenuTab()
    MaterialTheme {
        TabNavigator(menuTab) {
            Scaffold(
                content = {
                    Column(modifier = Modifier.padding(bottom = Dimens.tabHeight)) { CurrentTab() }
                },
                bottomBar = {
                    BottomNavigation(modifier = Modifier.height(Dimens.tabHeight)) {
                        TabNavigationItem(MenuTab())
                        TabNavigationItem(CartTab())
                        TabNavigationItem(ReadyTab())
                    }
                }
            )
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