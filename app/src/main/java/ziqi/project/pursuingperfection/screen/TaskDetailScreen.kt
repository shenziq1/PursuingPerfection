package ziqi.project.pursuingperfection.screen

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.card.AddMoreItemCard
import ziqi.project.pursuingperfection.common.card.ItemCard
import ziqi.project.pursuingperfection.common.topBar.TaskDetailTopBar
import ziqi.project.pursuingperfection.utils.shortConvert
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel


@Composable
fun TaskDetailScreen(
    onBackClick: () -> Unit,
    onTitleClick: (Int) -> Unit,
    onTimeClick: (Int) -> Unit,
    onPriorityClick: (Int) -> Unit,
    onCategoryClick: (Int) -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    var inEditId by remember { mutableStateOf(-1) }
    val id = uiState.value.id
    Scaffold(
        topBar = {
            TaskDetailTopBar(
                title = uiState.value.title,
                timeStart = uiState.value.timeStart.shortConvert(),
                timeEnd = uiState.value.timeEnd.shortConvert(),
                profilePhoto = uiState.value.profilePhoto,
                category = uiState.value.category,
                priority = uiState.value.priority,
                onBackClick = onBackClick,
                onTitleClick = {
                    onTitleClick(id)
                    inEditId = -2
                },
                onTimeClick = {
                    onTimeClick(id)
                    inEditId = -3
                },
                onPriorityClick = {
                    onPriorityClick(id)
                    inEditId = -4
                },
                onCategoryClick = {
                    onCategoryClick(id)
                    inEditId = -5
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn(state = lazyListState) {
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
                itemsIndexed(
                    items = uiState.value.contents,
                    key = { index, _ -> index }) { _, item ->
                    ItemCard(
                        item = item,
                        inEdit = item.id == inEditId,
                        onCardClick = { inEditId = it },
                        onCardRemove = {
                            coroutineScope.launch {
                                viewModel.removeTaskItem(it)
                            }
                            inEditId = -1
                        },
                        onCheckChange = { coroutineScope.launch { viewModel.updateCheckedStatus(it) } },
                        onEditFinish = {
                            coroutineScope.launch { viewModel.replaceTask(item, it) }
                            inEditId = -1
                        },
                        scroll = { coroutineScope.launch { lazyListState.animateScrollBy(it) } }
                    )
                }
                item {
                    val newItemId =
                        if (uiState.value.contents.isNotEmpty()) uiState.value.contents.last().id + 1 else 0
                    AddMoreItemCard(
                        inEdit = inEditId == newItemId,
                        newItemId = newItemId,
                        onCardClick = { inEditId = newItemId },
                        onEditFinish = {
                            coroutineScope.launch { viewModel.addTaskItem(it) }
                            inEditId = -1
                        },
                        onCardRemove = { inEditId = -1 },
                        scroll = {
                            coroutineScope.launch {
                                lazyListState.animateScrollBy(it)
                            }
                        })
                }
                item {
                    Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp / 5))
                }
            }
        }
    }
}


