package viewmodel

import menuitems.MenuItemType

class MenuViewModel(
    private val onNewItemClick: (MenuItemType) -> Unit,
    private val onExtrasClick: () -> Unit
) {
    fun onEvent(event: MenuEvent) {
        when (event) {
            MenuEvent.ExtrasEvent -> onExtrasClick()
            is MenuEvent.NewItemEvent -> onNewItemClick(event.type)
        }
    }
}

sealed class MenuEvent {
    data object ExtrasEvent: MenuEvent()
    data class NewItemEvent(val type: MenuItemType): MenuEvent()
}