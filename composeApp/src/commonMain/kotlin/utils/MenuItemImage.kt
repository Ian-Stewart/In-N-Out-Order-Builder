package utils

import constants.ImagePath
import menuitems.MenuItemType

class MenuItemImage {
    companion object {
        fun headerImageForType(menuItemType: MenuItemType): String {
            return when (menuItemType) {
                MenuItemType.BURGER -> ImagePath.BURGER_HEADER.path
                MenuItemType.SHAKE -> ImagePath.SHAKE_HEADER.path
                MenuItemType.SODY_POP -> ImagePath.DRINK_HEADER.path
                MenuItemType.FLOAT -> ImagePath.FLOAT_HEADER.path
                MenuItemType.FRIES -> ImagePath.FRIES_HEADER.path
            }
        }
    }
}