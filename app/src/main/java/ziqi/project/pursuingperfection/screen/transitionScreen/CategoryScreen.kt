package ziqi.project.pursuingperfection.screen.transitionScreen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
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
import ziqi.project.pursuingperfection.common.TransitionScreenAppBar
import ziqi.project.pursuingperfection.viewModel.TaskDetailViewModel

@Composable
fun CategoryScreen(onNextClick: () -> Unit, viewModel: TaskDetailViewModel = hiltViewModel()) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
    ) { padding ->
        Text(
            text = "Category ${viewModel.type} ${viewModel.id}",
            modifier = Modifier
                .clickable { onNextClick() }
                .padding(padding)
        )
    }
}

