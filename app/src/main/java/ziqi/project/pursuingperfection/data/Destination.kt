package ziqi.project.pursuingperfection.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination{
    val icon: ImageVector
    val route: String
}

object Home: Destination {
    override val icon = Icons.Default.Home
    override val route = "home"
    const val detail = "home/{id}"
    val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        }
    )
    fun passId(id: Int): String{
        return "home/$id"
    }
}

object Done: Destination {
    override val icon = Icons.Default.Done
    override val route = "done"
    const val detail = "done/{id}"
    val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        }
    )
    fun passId(id: Int): String{
        return "done/$id"
    }
}

object Settings: Destination {
    override val icon = Icons.Default.Settings
    override val route = "settings"
}

interface TemporaryDestination{
    val route: String

    val arguments: List<NamedNavArgument>
    fun passId(id: Int, type: String): String

}

object Category: TemporaryDestination{
    override val route = "{type}/category/{id}"
    override fun passId(id: Int, type: String): String{
        return "$type/category/$id"
    }
    override val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        },
        navArgument("type"){
            type = NavType.StringType
        }
    )
}

object Priority: TemporaryDestination{
    override val route = "{type}/priority/{id}"
    override fun passId(id: Int, type: String): String{
        return "$type/priority/$id"
    }
    override val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        },
        navArgument("type"){
            type = NavType.StringType
        }
    )

}

object Time: TemporaryDestination{
    override val route = "{type}/time/{id}"
    override fun passId(id: Int, type: String): String{
        return "$type/time/$id"
    }
    override val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        },
        navArgument("type"){
            type = NavType.StringType
        }
    )
}

object Title: TemporaryDestination{
    override val route = "{type}/title/{id}"
    override fun passId(id: Int, type: String): String{
        return "$type/title/$id"
    }
    override val arguments = listOf(
        navArgument("id"){
            type = NavType.IntType
        },
        navArgument("type"){
            type = NavType.StringType
        }
    )
}

val Destinations = listOf(Home, Done, Settings)
val SubDestinations = listOf(Category, Priority, Time, Title)

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