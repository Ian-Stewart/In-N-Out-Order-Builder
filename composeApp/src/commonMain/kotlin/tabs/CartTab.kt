package tabs

import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import viewmodel.CartViewModel
import views.CartComposable

class CartTab(
    private val cartViewModel: CartViewModel
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Cart"
            val icon = rememberVectorPainter(Icons.Default.Home)

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
        CartComposable(viewModel = cartViewModel)
    }
}

