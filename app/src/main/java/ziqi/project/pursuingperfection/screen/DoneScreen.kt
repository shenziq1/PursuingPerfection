package ziqi.project.pursuingperfection.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.card.CategoryCard
import ziqi.project.pursuingperfection.common.card.TaskOverviewCard
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.DoneListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoneScreen(
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DoneListViewModel = hiltViewModel()
) {
    var categoryName by remember {
        mutableStateOf("All")
    }

    var visible by remember {
        mutableStateOf(true)
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks = viewModel.checkedTasks.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val allCategory = CategoryUiState(name = "All")

    LaunchedEffect(Unit){
        viewModel.initialize()
        //viewModel.updateTaskList(categoryName)
    }

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
            ) {
                item {
                    CategoryCard(
                        categoryUiState = allCategory,
                        selected = categoryName == "All",
                        onClick = {
                            categoryName = it
                            viewModel.updateTaskList(categoryName)
                            coroutineScope.launch {
                                visible = false
                                delay(300)
                                taskOverViewListState.scrollToItem(0)
                                //taskOverViewListState.animateScrollToItem(0)
                                visible = true
                            }
                        }
                    )
                }
                items(items = categories.value) { categoryUiState ->
                    CategoryCard(
                        categoryUiState = categoryUiState,
                        selected = categoryUiState.name == categoryName,
                        onClick = {
                            categoryName = it
                            viewModel.updateTaskList(categoryName)
                            coroutineScope.launch {
                                visible = false
                                delay(300)
                                taskOverViewListState.scrollToItem(0)
                                visible = true

                            }
                        }
                    )
                }
                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            AnimatedVisibility(visible = visible) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = taskOverViewListState,
                ) {
                    items(
                        items = tasks.value,
                        key = { it.id }
                    ) { taskUiState ->
                        TaskOverviewCard(
                            currentChecked = true,
                            uiState = taskUiState,
                            onCheck = {
                                coroutineScope.launch {
                                    viewModel.uncheckTask(it)
                                }
                            },
                            onClick = onClick,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 5))
                    }
                }
            }
        }
    }

}
