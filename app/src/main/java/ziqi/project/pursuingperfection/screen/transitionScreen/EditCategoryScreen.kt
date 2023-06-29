package ziqi.project.pursuingperfection.screen.transitionScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.viewModel.CategoryViewModel
import ziqi.project.pursuingperfection.viewModel.editViewModel.EditCategoryViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditCategoryScreen(
    onBackClick: () -> Unit,
    onNextClick: (Int) -> Unit,
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    editCategoryViewModel: EditCategoryViewModel = hiltViewModel(),
) {
    val categories = categoryViewModel.categories.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val selectedCategoryName =
        editCategoryViewModel.uiState.collectAsStateWithLifecycle().value.category
    val id = editCategoryViewModel.uiState.collectAsStateWithLifecycle().value.id
    val defaultCategoryUiState = CategoryUiState()

    val category = editCategoryViewModel.category

    LaunchedEffect(Unit) {
        categoryViewModel.initialize()
        editCategoryViewModel.initialize()
    }

    var currentValue by remember {
        mutableStateOf(category)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "EditCategory")
        TextField(
            value = currentValue,
            onValueChange = { currentValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                coroutineScope.launch {
                    editCategoryViewModel.updateAllTasksCategory(defaultCategoryUiState.copy(name = currentValue))
                }
            })
        )
    }
}

