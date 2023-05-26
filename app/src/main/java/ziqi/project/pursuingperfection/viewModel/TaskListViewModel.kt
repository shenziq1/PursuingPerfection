package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.uiState.TaskUiState
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {
    private var _plannedTasks = MutableStateFlow<List<TaskUiState>>(emptyList())
    val plannedTasks = _plannedTasks.asStateFlow()
    private var _checkedTasks = MutableStateFlow<List<TaskUiState>>(emptyList())
    val checkedTasks = _checkedTasks.asStateFlow()

    init {
        _plannedTasks.value = taskRepository.getTasks(checked = false, category = "All")
        _checkedTasks.value = taskRepository.getTasks(checked = true, category = "All")
    }

    fun updateTaskList(checked: Boolean, category: String){
        if (checked) _checkedTasks.value = taskRepository.getTasks(true, category)
        else _plannedTasks.value = taskRepository.getTasks(false, category)
    }
}