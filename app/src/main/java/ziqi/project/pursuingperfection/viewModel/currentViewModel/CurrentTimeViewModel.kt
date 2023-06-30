package ziqi.project.pursuingperfection.viewModel.currentViewModel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CurrentTimeViewModel @Inject constructor(
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
        viewModelScope.launch {
            repository.getTaskById(id).filterNotNull().collect {
                _uiState.value = it.toTaskUiState()
            }
        }
    }

    fun updateNewTaskTime(timeStart: LocalDateTime, timeEnd: LocalDateTime) {
        _uiState.value = _uiState.value.copy(timeStart = timeStart, timeEnd = timeEnd)
    }

    suspend fun updateTaskToRepository() {
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
        Log.d("transition", id.toString())
    }
}