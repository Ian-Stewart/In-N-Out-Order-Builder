package utils

import platform.Foundation.NSUUID

// TODO: Does this actually work on ios? I think it should, but...
actual fun randomUUID(): String = NSUUID().UUIDString()