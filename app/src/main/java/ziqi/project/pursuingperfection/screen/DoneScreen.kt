package ziqi.project.pursuingperfection.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.CategoryCard
import ziqi.project.pursuingperfection.common.TaskOverviewCard
import ziqi.project.pursuingperfection.data.LocalCategoryDataProvider
import ziqi.project.pursuingperfection.screen.HomeScreen
import ziqi.project.pursuingperfection.viewModel.DoneListViewModel
import ziqi.project.pursuingperfection.viewModel.HomeListViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DoneScreen(
    modifier: Modifier = Modifier,
    viewModel: DoneListViewModel = hiltViewModel()
) {
    var categoryName by remember {
        mutableStateOf("All")
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks = viewModel.checkedTasks.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()


    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
            ) {
                items(items = LocalCategoryDataProvider.allCategories) { categoryUiState ->
                    CategoryCard(
                        categoryUiState = categoryUiState,
                        selected = categoryUiState.name == categoryName,
                        onClick = {
                            categoryName = it
                            viewModel.updateTaskList(categoryName)
                        })
                }
            }
            AnimatedContent(
                targetState = categoryName,
                transitionSpec = {
                    slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentScope.SlideDirection.Down
                    ).with(
                        slideOutOfContainer(
                            animationSpec = tween(300, easing = EaseOut),
                            towards = AnimatedContentScope.SlideDirection.Up
                        )
                    )
                }
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = taskOverViewListState
                ) {
                    items(
                        items = tasks.value,
                        key = { it.id }
                    ) { taskUiState ->
                        TaskOverviewCard(true, taskUiState, {
                            coroutineScope.launch {
                                viewModel.uncheckTask(it)
                            }
                        })
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }

        }
    }

}
