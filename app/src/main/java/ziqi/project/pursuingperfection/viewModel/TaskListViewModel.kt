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
class TaskListViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _plannedTasks: MutableStateFlow<List<TaskUiState>> = MutableStateFlow(emptyList())
    val plannedTasks: StateFlow<List<TaskUiState>> = _plannedTasks.asStateFlow()
    private var _checkedTasks: MutableStateFlow<List<TaskUiState>> = MutableStateFlow(emptyList())
    val checkedTasks: StateFlow<List<TaskUiState>> = _checkedTasks.asStateFlow()

    init {
        viewModelScope.launch{
            taskRepository.getPlannedTasks("All").collect{ taskEntities ->
                _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
            taskRepository.getCheckedTasks("All").collect{ taskEntities ->
                _checkedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
    }

    fun updateTaskList(checked: Boolean, category: String) {
        viewModelScope.launch{
            if (checked){
                taskRepository.getCheckedTasks(category).collect{ taskEntities ->
                    _checkedTasks.value = taskEntities.map { it.toTaskUiState() }
                }
            } else {
                taskRepository.getPlannedTasks(category).collect{ taskEntities ->
                    _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
                }
            }
        }
    }

    //
    fun insertTask() {
        viewModelScope.launch {
            taskRepository.insertTask(LocalTaskDataProvider.allTasks[0].toTaskEntity())
            taskRepository.insertTask(LocalTaskDataProvider.allTasks[1].toTaskEntity())
        }
    }

//    private var _plannedTasks = MutableStateFlow<List<TaskUiState>>(emptyList())
//    val plannedTasks = _plannedTasks.asStateFlow()
//    private var _checkedTasks = MutableStateFlow<List<TaskUiState>>(emptyList())
//    val checkedTasks = _checkedTasks.asStateFlow()
//    init {
//        _plannedTasks.value = taskRepository.getTasks(checked = false, category = "All")
//        _checkedTasks.value = taskRepository.getTasks(checked = true, category = "All")
//    }
//
//    fun updateTaskList(checked: Boolean, category: String){
//        if (checked) _checkedTasks.value = taskRepository.getTasks(true, category)
//        else _plannedTasks.value = taskRepository.getTasks(false, category)
//    }
}