package ziqi.project.pursuingperfection.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ziqi.project.pursuingperfection.common.BottomNavigationBar
import ziqi.project.pursuingperfection.common.topBar.TopAppSearchBar
import ziqi.project.pursuingperfection.common.topBar.TransitionScreenTopBar
import ziqi.project.pursuingperfection.data.Destinations
import ziqi.project.pursuingperfection.data.Done
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.Category
import ziqi.project.pursuingperfection.data.NewCategory
import ziqi.project.pursuingperfection.data.Priority
import ziqi.project.pursuingperfection.data.Time
import ziqi.project.pursuingperfection.data.Title
import ziqi.project.pursuingperfection.data.Settings
import ziqi.project.pursuingperfection.data.navigateSingleTopTo
import ziqi.project.pursuingperfection.data.navigateSingleTopToWithoutState
import ziqi.project.pursuingperfection.screen.transitionScreen.CategoryScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.NewCategoryScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.PriorityScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.TimeScreen
import ziqi.project.pursuingperfection.screen.transitionScreen.TitleScreen

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentNavDestination = currentBackStack?.destination
    val currentDestination = Destinations.find { it.route == currentNavDestination?.route } ?: Home

    var progressValue by remember {
        mutableStateOf(0f)
    }

    val animatedProgressValue by animateFloatAsState(
        targetValue = progressValue
    )

    var selectedCategory by rememberSaveable() {
        mutableStateOf("All")
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            when (currentNavDestination?.route) {
                Home.route -> {
                    progressValue = 0f
                    TopAppSearchBar(
                        route = currentNavDestination.route!!,
                        onResultClick = { navController.navigateSingleTopTo(Home.passId(it)) }
                    )
                }

                Done.route -> {
                    progressValue = 0f
                    TopAppSearchBar(
                        route = currentNavDestination.route!!,
                        onResultClick = { navController.navigateSingleTopTo(Done.passId(it)) }
                    )
                }

                Category.route -> {
                    if (currentBackStack?.arguments?.getString("type") == "new") {
                        progressValue = 0.25f
                        TransitionScreenTopBar(1,
                            animatedProgressValue,
                            { navController.navigateSingleTopToWithoutState(Home.route) },
                            { navController.navigateSingleTopToWithoutState(Home.route) }
                        )
                    }
                }

                Title.route -> {
                    if (currentBackStack?.arguments?.getString("type") == "new") {
                        progressValue = 0.5f
                        TransitionScreenTopBar(2,
                            animatedProgressValue,
                            { navController.navigateSingleTopToWithoutState(Home.route) },
                            { navController.navigateSingleTopToWithoutState(Home.route) }
                        )
                    }
                }

                Time.route -> {
                    if (currentBackStack?.arguments?.getString("type") == "new") {
                        progressValue = 0.75f
                        TransitionScreenTopBar(3,
                            animatedProgressValue,
                            { navController.navigateSingleTopToWithoutState(Home.route) },
                            { navController.navigateSingleTopToWithoutState(Home.route) }
                        )
                    }
                }

                Priority.route -> {
                    if (currentBackStack?.arguments?.getString("type") == "new") {
                        progressValue = 1f
                        TransitionScreenTopBar(4,
                            animatedProgressValue,
                            { navController.navigateSingleTopToWithoutState(Home.route) },
                            { navController.navigateSingleTopToWithoutState(Home.route) }
                        )
                    }
                }

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
            if (currentNavDestination?.route == Home.route)
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
            modifier = Modifier
                .padding(padding)
                .animateContentSize()
        ) {
            composable(route = Home.route) { navBackStackEntry ->
                Log.d("nav", navBackStackEntry.destination.route.toString())
                HomeScreen(
                    selectedCategory = selectedCategory,
                    onCategorySelect = { selectedCategory = it },
                    onTaskCardClick = { id -> navController.navigateSingleTopTo(Home.passId(id)) },

                    onNewClick = {
                        navController.navigate(
                            NewCategory.passCategory(
                                it, "new"
                            )
                        )
                    },
                    onEditClick = {
                        navController.navigate(
                            NewCategory.passCategory(
                                it, "edit"
                            )
                        )
                        Log.d("category", it)
                    },
                )
            }
            composable(
                route = Home.detail,
                arguments = Home.arguments
            ) { navBackStackEntry ->
                Log.d("nav", navBackStackEntry.destination.route.toString())
                Log.d("nav", navBackStackEntry.arguments?.getInt("id").toString())
                Log.d("nav", currentBackStack?.destination?.route.toString())
                TaskDetailScreen(
                    onBackClick = { navController.popBackStack() },
                    onCategoryClick = { navController.navigate(Category.passId(it, "edit")) },
                    onPriorityClick = { navController.navigate(Priority.passId(it, "edit")) },
                    onTimeClick = { navController.navigate(Time.passId(it, "edit")) },
                    onTitleClick = { navController.navigate(Title.passId(it, "edit")) }
                )
            }
            composable(route = Done.route) {
                DoneScreen(
                    selectedCategory = selectedCategory,
                    onCategorySelect = { selectedCategory = it },
                    onTaskCardClick = { id -> navController.navigateSingleTopTo(Done.passId(id)) },
                )
            }
            composable(
                route = Done.detail,
                arguments = Done.arguments
            ) {
                TaskDetailScreen(
                    onBackClick = { navController.popBackStack() },
                    onCategoryClick = { navController.navigate(Category.passId(it, "edit")) },
                    onPriorityClick = { navController.navigate(Priority.passId(it, "edit")) },
                    onTimeClick = { navController.navigate(Time.passId(it, "edit")) },
                    onTitleClick = { navController.navigate(Category.passId(it, "edit")) }
                )
            }
            composable(route = Settings.route) {
                SettingsScreen()
            }
            composable(
                route = Category.route,
                arguments = Category.arguments
            ) { navBackStackEntry ->
                val currentType = navBackStackEntry.arguments?.getString("type") ?: "new"
                CategoryScreen(
                    selectedCategory = selectedCategory,
                    onCategorySelect = {
                        selectedCategory = it
                        Log.d("selectedCategory2", selectedCategory)
                    },
                    onBackClick = { navController.popBackStack() },
                    onNextClick = {
                        Log.d("transition", it.toString())
                        if (currentType == "new") navController.navigate(Title.passId(it, "new"))
                        else navController.popBackStack()
                    }
                )
            }

            composable(
                route = NewCategory.route,
                arguments = NewCategory.arguments
            ) {
                NewCategoryScreen(
                    setNewCategory = { selectedCategory = it },
                    onNextClick = { navController.popBackStack() }
                )
            }

            composable(route = Title.route, arguments = Title.arguments) { navBackStackEntry ->
                val currentType = navBackStackEntry.arguments?.getString("type") ?: "new"
                TitleScreen(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = {
                        Log.d("transition", it.toString())
                        if (currentType == "new") navController.navigate(Time.passId(it, "new"))
                        else navController.popBackStack()
                    }
                )
            }
            composable(route = Time.route, arguments = Time.arguments) { navBackStackEntry ->
                val currentType = navBackStackEntry.arguments?.getString("type") ?: "new"
                TimeScreen(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = {
                        Log.d("transition", it.toString())
                        if (currentType == "new") navController.navigate(Priority.passId(it, "new"))
                        else navController.popBackStack()
                    }
                )
            }
            composable(
                route = Priority.route,
                arguments = Priority.arguments
            ) { navBackStackEntry ->
                val currentType = navBackStackEntry.arguments?.getString("type") ?: "new"
                PriorityScreen(
                    onBackClick = { navController.popBackStack() },
                    onNextClick = {
                        Log.d("transition", it.toString())
                        if (currentType == "new") navController.navigateSingleTopToWithoutState(
                            Home.passId(
                                it
                            )
                        )
                        else navController.popBackStack()
                    }
                )
            }
        }
    }
}