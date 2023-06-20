package ziqi.project.pursuingperfection.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ziqi.project.pursuingperfection.common.BottomNavigationBar
import ziqi.project.pursuingperfection.common.TopAppSearchBar
import ziqi.project.pursuingperfection.data.Destinations
import ziqi.project.pursuingperfection.data.Done
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.Category
import ziqi.project.pursuingperfection.data.Priority
import ziqi.project.pursuingperfection.data.Time
import ziqi.project.pursuingperfection.data.Title
import ziqi.project.pursuingperfection.data.Settings
import ziqi.project.pursuingperfection.data.navigateSingleTopTo
import ziqi.project.pursuingperfection.screen.transitionScreen.CategoryScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.PriorityScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.TimeScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.TitleScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentNavDestination = currentBackStack?.destination
    val currentDestination = Destinations.find { it.route == currentNavDestination?.route } ?: Home
    Scaffold(
        modifier = modifier,
        topBar = {
            when (currentNavDestination?.route) {
                Home.route -> TopAppSearchBar(
                    route = currentNavDestination.route!!,
                    onResultClick = { navController.navigateSingleTopTo(Home.passId(it)) })

                Done.route -> TopAppSearchBar(
                    route = currentNavDestination.route!!,
                    onResultClick = { navController.navigateSingleTopTo(Done.passId(it)) })

                else -> {}
            }
        },
        bottomBar = {
            if (currentNavDestination?.route in listOf(Home.route, Done.route, Settings.route))
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
                    onClick = { navController.navigate(Category.passId(-1, "new")) }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = Modifier.padding(padding).animateContentSize()
        ) {
            composable(route = Home.route) {
                Log.d("nav", it.destination.route.toString())
                HomeScreen({ id -> navController.navigateSingleTopTo(Home.passId(id)) })
            }
            composable(
                route = Home.detail,
                arguments = Home.arguments
            ) { navBackStackEntry ->
                Log.d("nav", navBackStackEntry.destination.route.toString())
                Log.d("nav", navBackStackEntry.arguments?.getInt("id").toString())
                Log.d("nav", currentBackStack?.destination?.route.toString())
                TaskScreen(
                    onBackClick = { navController.popBackStack() },
                    onCategoryClick = { navController.navigate(Category.passId(it, "edit"))},
                    onPriorityClick = { navController.navigate(Priority.passId(it, "edit"))},
                    onTimeClick = { navController.navigate(Time.passId(it, "edit"))},
                    onTitleClick = { navController.navigate(Category.passId(it, "edit"))}
                )
            }
            composable(route = Done.route) {
                DoneScreen({ id -> navController.navigate(Done.passId(id)) })
            }
            composable(
                route = Done.detail,
                arguments = Done.arguments
            ) { navBackStackEntry ->
                Log.d("nav", navBackStackEntry.destination.route.toString())
                Log.d("nav", navBackStackEntry.arguments?.getInt("id").toString())
                Log.d("nav", currentBackStack?.destination?.route.toString())
                TaskScreen(
                    onBackClick = { navController.popBackStack() },
                    onCategoryClick = { navController.navigate(Category.passId(it, "edit"))},
                    onPriorityClick = { navController.navigate(Priority.passId(it, "edit"))},
                    onTimeClick = { navController.navigate(Time.passId(it, "edit"))},
                    onTitleClick = { navController.navigate(Category.passId(it, "edit"))}
                )
            }
            composable(route = Settings.route) {
                SettingsScreen()
            }
            composable(route = Category.route, arguments = Category.arguments) {
                val currentId = it.arguments?.getInt("id") ?: -1
                CategoryScreen(onNextClick = {
                    if (currentId == -1) navController.navigate(Title.passId(currentId, "new"))
                    else navController.popBackStack()
                })
            }
            composable(route = Title.route, arguments = Title.arguments) {
                val currentId = it.arguments?.getInt("id") ?: -1
                TitleScreen(onNextClick = {
                    if (currentId == -1) navController.navigate(Time.passId(currentId, "new"))
                    else navController.popBackStack()
                })
            }
            composable(route = Time.route, arguments = Time.arguments) {
                val currentId = it.arguments?.getInt("id") ?: -1
                TimeScreen(onNextClick = {
                    if (currentId == -1) navController.navigate(Priority.passId(currentId, "new"))
                    else navController.popBackStack()
                })
            }
            composable(route = Priority.route, arguments = Priority.arguments) {
                val currentId = it.arguments?.getInt("id") ?: -1
                PriorityScreen(onNextClick = {
                    if (currentId == -1) navController.navigate(Home.route)
                    else navController.popBackStack()
                })
            }
        }
    }
}