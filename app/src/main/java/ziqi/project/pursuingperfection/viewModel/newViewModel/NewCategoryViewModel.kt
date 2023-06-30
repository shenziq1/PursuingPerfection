package ziqi.project.pursuingperfection.viewModel.newViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    val category = savedStateHandle.get<String>("category") ?: "new"
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun updateAllTasksCategory(newCategory: String, newIcon: Int = R.drawable.ic_launcher_foreground){
        viewModelScope.launch{
            repository.updateTaskCategory(category, newCategory, newIcon)
        }
    }
}