package ziqi.project.pursuingperfection.screen

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ziqi.project.pursuingperfection.common.BottomNavigationBar
import ziqi.project.pursuingperfection.common.TopAppSearchBar
import ziqi.project.pursuingperfection.data.Destinations
import ziqi.project.pursuingperfection.data.Done
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.Settings
import ziqi.project.pursuingperfection.data.navigateSingleTopTo

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentNavDestination = currentBackStack?.destination
    val currentDestination = Destinations.find { it.route == currentNavDestination?.route } ?: Home
    Scaffold(
        modifier = modifier,
        topBar = {
            if (currentNavDestination?.route in listOf(Home.route, Done.route)) {
                TopAppSearchBar(currentNavDestination?.route!!)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navigateTo = { navController.navigateSingleTopTo(it) },
                currentDestination = currentDestination
            )
        },
        floatingActionButton = {
            if (currentNavDestination?.route in listOf(Home.route, Done.route))
                FloatingActionButton(
                    modifier = Modifier,
                    shape = MaterialTheme.shapes.medium,
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = Home.route) {
                Log.d("nav", it.destination.route.toString())
                HomeScreen({id -> navController.navigate(Home.passId(id))})
            }
            composable(
                route = Home.detail,
                arguments = Home.arguments
            ) {
                Log.d("nav", it.destination.route.toString())
                Log.d("nav", it.arguments?.getInt("id").toString())
                Log.d("nav", currentBackStack?.destination?.route.toString())
                TaskScreen()
            }
            composable(route = Done.route) {
                DoneScreen({id -> navController.navigate(Home.detail)})
            }
            composable(route = Settings.route) {
                SettingsScreen()
            }

        }

    }
}