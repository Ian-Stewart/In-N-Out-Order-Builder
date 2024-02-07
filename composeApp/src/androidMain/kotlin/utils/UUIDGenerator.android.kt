package utils

import java.util.UUID

actual fun UUIDGenerator(): String = UUID.randomUUID().toString()