package ziqi.project.pursuingperfection.screen

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
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
import ziqi.project.pursuingperfection.viewModel.HomeListViewModel


@SuppressLint("UnusedCrossfadeTargetStateParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    onClick: (Int)-> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeListViewModel = hiltViewModel(),
) {
    var categoryName by remember {
        mutableStateOf("All")
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks = viewModel.plannedTasks.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()



    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp, bottom = 8.dp)
            ) {
                items(
                    items = LocalCategoryDataProvider.allCategories,
                    key = { it.id }
                ) { categoryUiState ->
                    CategoryCard(
                        categoryUiState = categoryUiState,
                        selected = categoryUiState.name == categoryName,
                        onClick = {
                            categoryName = it
                            viewModel.updateTaskList(categoryName)
                            coroutineScope.launch {
                                taskOverViewListState.scrollToItem(0, 0)
                            }
                        }
                    )
                }
            }
            Box(modifier = Modifier.animateContentSize()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = taskOverViewListState
                ) {
                    items(
                        items = tasks.value,
                        key = { it.id }
                    ) { taskUiState ->
                        TaskOverviewCard(
                            currentChecked = false,
                            taskUiState = taskUiState,
                            onCheck = {
                                coroutineScope.launch {
                                    viewModel.checkTask(it)
                                }
                            },
                            onClick = onClick
                        )
                    }
                    item { Spacer(modifier = Modifier.height(8.dp)) }
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxSize(1f),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.insertAllTasks()
                }
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
            Button(onClick = {
                coroutineScope.launch {
                    viewModel.deleteAllTasks()
                }
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }

        }
    }

}
