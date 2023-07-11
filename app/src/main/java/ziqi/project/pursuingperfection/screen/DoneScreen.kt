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
import ziqi.project.pursuingperfection.common.topBar.CategoryReadOnlyBar
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.DoneListViewModel
import ziqi.project.pursuingperfection.viewModel.HomeListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoneScreen(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onTaskCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DoneListViewModel = hiltViewModel(),
) {

    var visible by remember {
        mutableStateOf(true)
    }
    val taskOverViewListState = rememberLazyListState()
    val tasks = viewModel.checkedTasks.collectAsStateWithLifecycle()

    LaunchedEffect(selectedCategory) {
        viewModel.updateTaskList(selectedCategory)
    }

    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            CategoryReadOnlyBar(
                selectedCategory = selectedCategory,
                onCategoryClick = {
                    viewModel.updateTaskList(it)
                    onCategorySelect(it)
                },
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
    }
}
