package ziqi.project.pursuingperfection.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    var plannedTasks: StateFlow<List<TaskUiState>> =
        taskRepository.getPlannedTasks("All").map { taskEntities ->
            taskEntities.map { it.toTaskUiState() }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            listOf(TaskUiState())
        )

    fun updateTaskList(checked: Boolean, category: String) {
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