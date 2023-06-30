package ziqi.project.pursuingperfection.screen.transitionScreen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.common.TaskTextField
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.viewModel.currentViewModel.CurrentTitleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreen(
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    viewModel: CurrentTitleViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var inEdit by remember {
        mutableStateOf(true)
    }
    var title by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        viewModel.initialize()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Set up a title for your task:",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            //Spacer(modifier = Modifier.height())
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(80.dp)
                    .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 16.dp)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
                ),
                onClick = { inEdit = !inEdit }
            ) {
                Row(
                    modifier = Modifier
                        .heightIn(80.dp)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (inEdit) {
                        true -> {
                            TaskTextField(
                                item = Item(content = title),
                                onEditFinish = {
                                    viewModel.updateNewTaskTitle(it.content)
                                    title = it.content
                                    inEdit = false
                                },
                                onCardRemove = {},
                                scroll = {},
                                modifier = Modifier,
                                textStyle = MaterialTheme.typography.headlineMedium,
                                maxLines = 2,
                                defaultValueOn = true,
                                defaultValue = "untitled"
                            )
                        }

                        false -> {
                            Text(text = title, style = MaterialTheme.typography.headlineMedium)
                        }
                    }

                }
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
                Log.d("test title", viewModel.uiState.value.title)
            }, shape = MaterialTheme.shapes.small) {
                Text(text = "Next")
            }
        }
    }
}