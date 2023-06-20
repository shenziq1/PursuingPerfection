package ziqi.project.pursuingperfection.screen.transitionScreen

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel

@Composable
fun PriorityScreen(onNextClick: () -> Unit, viewModel: TaskDetailViewModel = hiltViewModel()) {
   Text(text = "Priority ${viewModel.type} ${viewModel.id}", modifier = Modifier.clickable { onNextClick() })
}