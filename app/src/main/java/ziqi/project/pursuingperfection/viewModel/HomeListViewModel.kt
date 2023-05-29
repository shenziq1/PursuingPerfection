package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.LocalTaskDataProvider
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _plannedTasks: MutableStateFlow<List<TaskUiState>> = MutableStateFlow(emptyList())
    val plannedTasks: StateFlow<List<TaskUiState>> = _plannedTasks.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getPlannedTasks("All").collect { taskEntities ->
                _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
    }

    fun updateTaskList(category: String) {
        viewModelScope.launch {
            taskRepository.getPlannedTasks(category).collect { taskEntities ->
                _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
    }

    //
    suspend fun insertTask() {
        viewModelScope.launch {
            taskRepository.insertTask(LocalTaskDataProvider.allTasks[0].toTaskEntity())
            taskRepository.insertTask(LocalTaskDataProvider.allTasks[1].toTaskEntity())
        }
    }

    suspend fun checkTask(taskUiState: TaskUiState) {
        viewModelScope.launch {
            taskRepository.updateTask(taskUiState.toTaskEntity().copy(checked = true))
        }
    }

}