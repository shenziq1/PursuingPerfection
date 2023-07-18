package ziqi.project.pursuingperfection.viewModel.currentViewModel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class CurrentPriorityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if(initializeCalled) return
        initializeCalled = true
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTaskById(id).filterNotNull().collect {
                _uiState.value = it.toTaskUiState()
            }
        }
    }

    fun updateNewTaskPriority(priority: Int) {
        _uiState.value = _uiState.value.copy(priority = priority)
        Log.d("priority", priority.toString())
    }

    suspend fun updateTaskToRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }
}