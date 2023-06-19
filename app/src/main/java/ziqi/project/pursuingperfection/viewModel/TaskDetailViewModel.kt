package ziqi.project.pursuingperfection.viewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.TaskEntity
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    private val id = savedStateHandle.get<Int>("id") ?: 0
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getTaskById(id).collect {
                _uiState.value = it.toTaskUiState()
            }
        }
    }

    suspend fun updateCheckedStatus(task: Item){
        _uiState.value =
            _uiState.value.copy(contents = _uiState.value.contents.map {
                if (task == it) Item(
                    it.id,
                    it.content,
                    !it.checked
                ) else it
            })
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun replaceTask(old: Item, new: Item){
        _uiState.value =
            _uiState.value.copy(contents = _uiState.value.contents.map {
                if (old == it) new else it
            })
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun addTaskItem(task: Item){
        _uiState.value = _uiState.value.copy(contents = _uiState.value.contents + task)
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun removeTaskItem(task: Item){
        _uiState.value = _uiState.value.copy(contents = _uiState.value.contents - task)
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

}