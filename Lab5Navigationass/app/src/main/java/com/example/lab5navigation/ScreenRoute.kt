

import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController



sealed class ScreenRoute(val route: String) {
    object First : ScreenRoute("page1")
    object Second : ScreenRoute("page2")
}
