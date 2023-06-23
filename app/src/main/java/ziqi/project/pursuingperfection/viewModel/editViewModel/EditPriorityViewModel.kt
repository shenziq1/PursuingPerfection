package ziqi.project.pursuingperfection.viewModel.editViewModel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class EditPriorityViewModel @Inject constructor(
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
                viewModelScope.launch {
                    repository.getMostRecentTask().collect {
                        _uiState.value = it.toTaskUiState()
                    }
                }
            }
        }
    }

    fun updateNewTaskPriority(priority: Int) {
        _uiState.value = _uiState.value.copy(priority = priority)
        Log.d("priority", priority.toString())
    }

    suspend fun updateTaskToRepository() {
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }
}