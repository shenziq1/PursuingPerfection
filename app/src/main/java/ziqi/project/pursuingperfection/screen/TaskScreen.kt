package ziqi.project.pursuingperfection.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel


@Composable
fun TaskScreen(viewModel: TaskDetailViewModel = hiltViewModel()) {
    Text(text = "Task Screen ${viewModel.id}")
}