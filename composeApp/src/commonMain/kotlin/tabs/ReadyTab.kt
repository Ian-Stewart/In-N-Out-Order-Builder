package tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import viewmodel.OrderViewModel

class ReadyTab(private val viewModel: OrderViewModel) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Ready"
            val icon = rememberVectorPainter(Icons.Default.AccountBox)

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
        Column {
            TextField(value = "Ready to Order?", onValueChange = {})
            val state = viewModel.stateFlow.collectAsState()
            val cartItems = state.value.orderItems
            LazyColumn {
                items(items = cartItems, itemContent = { item -> Text(text = item)})
            }
        }
    }
}