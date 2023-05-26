package ziqi.project.pursuingperfection.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

interface Destination{
    val icon: ImageVector
    val route: String
}

object Home: Destination {
    override val icon = Icons.Default.Home
    override val route = "home"
}

object Done: Destination {
    override val icon = Icons.Default.Done
    override val route = "done"
}

object Settings: Destination {
    override val icon = Icons.Default.Settings
    override val route = "settings"
}

val Destinations = listOf(Home, Done, Settings)

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }