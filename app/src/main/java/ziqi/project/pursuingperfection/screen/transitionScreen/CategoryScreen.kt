package ziqi.project.pursuingperfection.screen.transitionScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ziqi.project.pursuingperfection.common.CategoryCard
import ziqi.project.pursuingperfection.common.TransitionScreenAppBar
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.CategoryViewModel
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel

@Composable
fun CategoryScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    taskDetailViewModel: TaskDetailViewModel = hiltViewModel(),
) {
    val categories = categoryViewModel.categories.collectAsStateWithLifecycle()
    var selectedCategory by remember {
        mutableStateOf("All")
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Select a category from below:",
                style = MaterialTheme.typography.titleMedium
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            item {
                CategoryCard(
                    categoryUiState = CategoryUiState(),
                    selected = selectedCategory == "All",
                    onClick = {selectedCategory = it},
                    modifier = Modifier.aspectRatio(1f),
                    shape = MaterialTheme.shapes.large,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            items(items = categories.value, key = { it.id }) { categoryUiState ->
                CategoryCard(
                    categoryUiState = categoryUiState,
                    selected = selectedCategory == categoryUiState.name,
                    onClick = { selectedCategory = it },
                    modifier = Modifier.aspectRatio(1f),
                    shape = MaterialTheme.shapes.large,
                    style = MaterialTheme.typography.titleLarge
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { /*TODO*/ }, shape = MaterialTheme.shapes.small) {
                Text(text = "Create New Category")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onBackClick, shape = MaterialTheme.shapes.small) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = onNextClick, shape = MaterialTheme.shapes.small) {
                Text(text = "Next")
            }
        }
    }
}

