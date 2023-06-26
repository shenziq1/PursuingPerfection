package ziqi.project.pursuingperfection.viewModel.editViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class EditCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()
    private val type = savedStateHandle.get<String>("type") ?: "edit"

    init {
        when (type) {
            "edit" -> {
                viewModelScope.launch {
                    repository.getTaskById(id).collect {
                        _uiState.value = it.toTaskUiState()
                    }
                }
            }

            "new" -> {
                val newTaskUiState = TaskUiState()
                viewModelScope.launch {
                    val id = repository.insertTask(newTaskUiState.toTaskEntity())
                    _uiState.value = _uiState.value.copy(id = id.toInt())
                }
            }
        }
    }

    fun updateNewTaskCategory(categoryUiState: CategoryUiState) {
        _uiState.value = _uiState.value.copy(
            category = categoryUiState.name,
            profilePhoto = categoryUiState.picture
        )
    }

    suspend fun updateTaskToRepository() {
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }
}