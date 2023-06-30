package ziqi.project.pursuingperfection.screen.transitionScreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.viewModel.newViewModel.NewCategoryViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditCategoryScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: NewCategoryViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    var currentValue by remember {
        mutableStateOf("new")
    }


    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text(text = "EditCategory")
        TextField(
            value = currentValue,
            onValueChange = { currentValue = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                coroutineScope.launch{
                    viewModel.updateAllTasksCategory(currentValue)
                    onNextClick()
                }
                keyboardController?.hide()

            })
        )
    }
}

