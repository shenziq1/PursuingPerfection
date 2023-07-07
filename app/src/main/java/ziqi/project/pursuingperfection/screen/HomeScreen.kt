package ziqi.project.pursuingperfection.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.card.CategoryCard
import ziqi.project.pursuingperfection.common.card.CategoryCardVariant
import ziqi.project.pursuingperfection.common.card.TaskOverviewCard
import ziqi.project.pursuingperfection.common.topBar.CategoryBar
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.HomeListViewModel
import ziqi.project.pursuingperfection.viewModel.newViewModel.NewCategoryViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onTaskCardClick: (Int) -> Unit,
    onNewClick: (String) -> Unit,
    onEditClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeListViewModel = hiltViewModel(),
) {
    var categoryName by rememberSaveable {
        mutableStateOf("All")
    }
    var visible by remember {
        mutableStateOf(true)
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks = viewModel.plannedTasks.collectAsStateWithLifecycle()
    val categories = viewModel.categories.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val allCategory = CategoryUiState(category = "All")

    LaunchedEffect(Unit) {
        viewModel.initialize()
        //viewModel.updateTaskList(categoryName)
    }

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryBar(
                onNewClick = onNewClick,
                onEditClick = onEditClick,
                onCategoryClick = { viewModel.updateTaskList(it) },
                setVisible = { visible = it }
            )
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
                            currentChecked = false,
                            uiState = taskUiState,
                            onClick = onTaskCardClick,
                            modifier = Modifier.animateItemPlacement()
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 5))
                    }
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

