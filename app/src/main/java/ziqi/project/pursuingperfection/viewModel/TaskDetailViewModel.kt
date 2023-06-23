package ziqi.project.pursuingperfection.viewModel

import android.util.Log
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
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    private var _uiState = MutableStateFlow(TaskUiState())
    val uiState = _uiState.asStateFlow()
    val type = savedStateHandle.get<String>("type") ?: "edit"


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
                _uiState.value = TaskUiState()
            }
        }

    }

    fun updateNewTaskCategory(categoryUiState: CategoryUiState) {
        _uiState.value = _uiState.value.copy(
            category = categoryUiState.name,
            profilePhoto = categoryUiState.picture
        )
    }

    fun updateNewTaskTitle(title: String){
        _uiState.value = _uiState.value.copy(title = title)
    }

    fun updateNewTaskPriority(priority: Int){
        _uiState.value = _uiState.value.copy(priority = priority)
    }

    suspend fun addNewTaskToRepository() {
        viewModelScope.launch {
            repository.insertTask(_uiState.value.toTaskEntity())
        }
    }



    suspend fun updateCheckedStatus(item: Item) {
        _uiState.value =
            _uiState.value.copy(contents = _uiState.value.contents.map {
                if (item == it) Item(
                    it.id,
                    it.content,
                    !it.checked
                ) else it
            })
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun replaceTask(old: Item, new: Item) {
        _uiState.value =
            _uiState.value.copy(contents = _uiState.value.contents.map {
                if (old == it) new else it
            })
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun addTaskItem(item: Item) {
        _uiState.value = _uiState.value.copy(contents = _uiState.value.contents + item)
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

    suspend fun removeTaskItem(item: Item) {
        _uiState.value = _uiState.value.copy(contents = _uiState.value.contents - item)
        viewModelScope.launch {
            repository.updateTask(_uiState.value.toTaskEntity())
        }
    }

}