package viewmodel

import menuitems.MenuItemType

class MenuViewModel() {
    private var onNewItemClick: (MenuItemType) -> Unit = {}
    private var onExtrasClick: () -> Unit = {}

    fun bindOnNewItemClick(onNew: (MenuItemType) -> Unit) {
        onNewItemClick = onNew
    }

    fun bindOnExtrasClick(onExtras: () -> Unit) {
        onExtrasClick = onExtras
    }

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