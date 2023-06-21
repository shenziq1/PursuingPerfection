package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toCategoryUiState
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {

    private var _categories: MutableStateFlow<List<CategoryUiState>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<CategoryUiState>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getAllCategories().collect { taskEntities ->
                _categories.value = taskEntities.map { it.toCategoryUiState() }
            }
        }
    }
}