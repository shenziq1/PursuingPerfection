package ziqi.project.pursuingperfection.screen.transitionScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.common.card.PriorityCard
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel
import ziqi.project.pursuingperfection.viewModel.editViewModel.EditPriorityViewModel

@Composable
fun PriorityScreen(
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    viewModel: EditPriorityViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val selected = when (viewModel.uiState.collectAsStateWithLifecycle().value.priority){
        0 -> "High"
        1 -> "Medium"
        else -> "Low"
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

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            PriorityCard(
                selected = selected == "High",
                priority = "High",
                painterRes = R.drawable.bolt,
                onClick = {
                    viewModel.updateNewTaskPriority(0)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            PriorityCard(
                selected = selected == "Medium",
                priority = "Medium",
                painterRes = R.drawable.cloudy,
                onClick = {
                    viewModel.updateNewTaskPriority(1)
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            PriorityCard(
                selected = selected == "Low",
                priority = "Low",
                painterRes = R.drawable.sunny,
                onClick = {
                    viewModel.updateNewTaskPriority(2)
                }
            )
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