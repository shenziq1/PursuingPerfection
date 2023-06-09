package ziqi.project.pursuingperfection.screen.transitionScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.card.CategoryCard
import ziqi.project.pursuingperfection.viewModel.currentViewModel.CurrentCategoryViewModel

@Composable
fun CategoryScreen(
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    viewModel: CurrentCategoryViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.initialize1()
        viewModel.initialize2(selectedCategory)
    }

    val coroutineScope = rememberCoroutineScope()
    val selectedCategoryName = viewModel.taskUiState.collectAsStateWithLifecycle().value.category
    val selectedTaskId = viewModel.taskUiState.collectAsStateWithLifecycle().value.id
    val categories = viewModel.categoryUiState.collectAsStateWithLifecycle().value

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
        Spacer(modifier = Modifier.height(16.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.weight(1f)
        ) {

            items(items = categories, key = { it.id }) { categoryUiState ->
                CategoryCard(
                    categoryUiState = categoryUiState,
                    selected = selectedCategoryName == categoryUiState.category,
                    onClick = {
                        coroutineScope.launch {
                            viewModel.saveTaskToRepository(categoryUiState)
                        }
                        onCategorySelect(it)
                    },
                    modifier = Modifier.aspectRatio(1f),
                    shape = MaterialTheme.shapes.large,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onBackClick()
                coroutineScope.launch {
                    viewModel.cancelTask()
                }
            }, shape = MaterialTheme.shapes.small) {
                Text(text = "Back")
            }
            Spacer(modifier = Modifier.width(24.dp))
            Button(onClick = {
                onNextClick(selectedTaskId)
                Log.d("whyyyyy", selectedCategoryName)
            }, shape = MaterialTheme.shapes.small) {
                Text(text = "Next")
            }
        }
    }
}

