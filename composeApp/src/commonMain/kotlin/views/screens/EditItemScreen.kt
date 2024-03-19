package views.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import viewmodel.NewEditEvent
import viewmodel.NewEditViewModel
import views.ItemDetail
import views.ItemDetailsError
import views.ItemDetailsLoading

class EditItemScreen(private val cartItemUuid: String) : Screen {
    @Composable
    override fun Content() {
        val newEditViewModel: NewEditViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow

        // Yes, side-effects in LaunchedEffect are bad
        // However for a small app like this I strongly prefer this to like
        // Nav-scoped viewmodels or something?
        LaunchedEffect(cartItemUuid) {
            newEditViewModel.bindOnDone { navigator.pop() }
            newEditViewModel.onEvent(NewEditEvent.EditExistingItemEvent(cartItemUuid))
        }

        val state = newEditViewModel.stateFlow.collectAsState()
        val cartItem = state.value.item
        if (cartItem == null) {
            ItemDetailsLoading()
        } else if (state.value.error) {
            ItemDetailsError()
        } else {
            ItemDetail(
                cartItem = cartItem,
                isNewItem = state.value.newItem,
                eventHandler = newEditViewModel::onEvent
            )
        }
    }
}