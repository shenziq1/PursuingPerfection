package ziqi.project.pursuingperfection

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

interface Destination{
    val icon: ImageVector
    val route: String
}

object Home: Destination{
    override val icon = Icons.Default.Home
    override val route = "home"
}

object Done: Destination{
    override val icon = Icons.Default.Done
    override val route = "done"
}

object Settings: Destination{
    override val icon = Icons.Default.Settings
    override val route = "settings"
}

val Destinations = listOf(Home, Done, Settings)