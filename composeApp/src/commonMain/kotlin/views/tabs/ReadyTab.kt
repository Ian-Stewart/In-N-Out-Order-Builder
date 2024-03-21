package views.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import constants.Dims
import org.koin.compose.koinInject
import viewmodel.OrderItem
import viewmodel.OrderViewModel

class ReadyTab : Tab {
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
        val orderViewModel: OrderViewModel = koinInject()
        Column {
            Text(text = "Ready to Order?", style = MaterialTheme.typography.h4)
            val state = orderViewModel.stateFlow.collectAsState()
            val cartItems = state.value.orderItems
            LazyColumn {
                items(items = cartItems, itemContent = { item -> ItemRow(item)})
            }
        }
    }
}

@Composable
fun ItemRow(orderItem: OrderItem, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier.align(Alignment.Top)
        ) {
            Text(
                text = "${orderItem.count}x",
                modifier = Modifier.padding(Dims.smPad),
                style = MaterialTheme.typography.h5
                )
        }
        Text(
            text = orderItem.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Dims.smPad, bottom = Dims.smPad, top = 12.dp, end = Dims.smPad)
        )
    }
}