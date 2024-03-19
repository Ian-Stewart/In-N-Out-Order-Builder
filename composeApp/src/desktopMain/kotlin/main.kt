import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import di.initKoin
import org.koin.core.context.startKoin

fun main() = application {
    initKoin()
    val state = rememberWindowState(
        size = DpSize(400.dp, 800.dp),
        position = WindowPosition(50.dp, 50.dp)
    )
    Window(
        onCloseRequest = ::exitApplication,
        title = "In-N-Out Order Calculator",
        state = state
    ) {
        App()
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}