package ziqi.project.pursuingperfection.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.SmallAvatar
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel


@Composable
fun TaskScreen(onBackClick: () -> Unit, viewModel: TaskDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TaskTopBar(
                title = uiState.value.title,
                timeCreated = uiState.value.timeCreated.toString(),
                profilePhoto = uiState.value.profilePhoto,
                category = uiState.value.category,
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }, shape = MaterialTheme.shapes.medium) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
            items(items = uiState.value.contents) {
                TaskCard(
                    content = it,
                    onCheckChange = { coroutineScope.launch { viewModel.updateCheckedStatus(it) } })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTopBar(
    title: String,
    timeCreated: String,
    profilePhoto: Int,
    category: String,
    onBackClick: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                modifier = Modifier.padding(end = 16.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            Text(text = category, style = MaterialTheme.typography.labelLarge)
            SmallAvatar(profilePhoto)
            Text(text = "Apr.8 - Aug.6", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.width(16.dp))
        }
    )
}

@Composable
fun TaskCard(content: Item, onCheckChange: (Item) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(80.dp)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
    ) {
        when (content.checked) {
            true -> Row(
                modifier = Modifier.heightIn(80.dp)
                    .padding(start = 4.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = true, onCheckedChange = { onCheckChange(content) })
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = content.content, textDecoration = TextDecoration.LineThrough)
            }

            false -> Row(
                modifier = Modifier.heightIn(80.dp)
                    .padding(start = 4.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = false, onCheckedChange = { onCheckChange(content) })
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = content.content)
            }
        }
    }

}