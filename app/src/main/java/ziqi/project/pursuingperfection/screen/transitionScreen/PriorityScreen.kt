package ziqi.project.pursuingperfection.screen.transitionScreen

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
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.common.card.PriorityCard
import ziqi.project.pursuingperfection.viewModel.currentViewModel.CurrentPriorityViewModel

@Composable
fun PriorityScreen(
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    viewModel: CurrentPriorityViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val selected = when (viewModel.uiState.collectAsStateWithLifecycle().value.priority) {
        0 -> "High"
        1 -> "Medium"
        else -> "Low"
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Set up a priority for your task:",
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
            item {
                PriorityCard(
                    selected = selected == "High",
                    priority = "High",
                    painterRes = R.drawable.bolt,
                    onClick = {
                        viewModel.updateNewTaskPriority(0)
                    },
                    modifier = Modifier.aspectRatio(1f)
                )
            }
            item {
                PriorityCard(
                    selected = selected == "Medium",
                    priority = "Medium",
                    painterRes = R.drawable.cloudy,
                    onClick = {
                        viewModel.updateNewTaskPriority(1)
                    },
                    modifier = Modifier.aspectRatio(1f)

                )
            }
            item {
                PriorityCard(
                    selected = selected == "Low",
                    priority = "Low",
                    painterRes = R.drawable.sunny,
                    onClick = {
                        viewModel.updateNewTaskPriority(2)
                    },
                    modifier = Modifier.aspectRatio(1f)
                )
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
            Button(onClick = {
                onNextClick(viewModel.id)
                coroutineScope.launch {
                    viewModel.updateTaskToRepository()
                }
            }, shape = MaterialTheme.shapes.small) {
                Text(text = "Next")
            }
        }
    }
}