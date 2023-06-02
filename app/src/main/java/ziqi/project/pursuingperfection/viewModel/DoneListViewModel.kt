package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject


@HiltViewModel
class DoneListViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _checkedTasks: MutableStateFlow<List<TaskUiState>> = MutableStateFlow(emptyList())
    val checkedTasks: StateFlow<List<TaskUiState>> = _checkedTasks.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getCheckedTasks("All").collect { taskEntities ->
                _checkedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
    }

    fun updateTaskList(category: String) {
        viewModelScope.launch {
            taskRepository.getCheckedTasks(category).collect { taskEntities ->
                _checkedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
    }

    suspend fun uncheckTask(taskUiState: TaskUiState) {
        viewModelScope.launch {
            taskRepository.updateTask(taskUiState.toTaskEntity().copy(checked = false))
        }
    }
}
