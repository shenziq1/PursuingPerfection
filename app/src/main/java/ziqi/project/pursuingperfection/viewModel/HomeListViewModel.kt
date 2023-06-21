package ziqi.project.pursuingperfection.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.LocalTaskDataProvider
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toCategoryUiState
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class HomeListViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _plannedTasks: MutableStateFlow<List<TaskUiState>> = MutableStateFlow(emptyList())
    val plannedTasks: StateFlow<List<TaskUiState>> = _plannedTasks.asStateFlow()

    private var _categories: MutableStateFlow<List<CategoryUiState>> = MutableStateFlow(emptyList())
    val categories: StateFlow<List<CategoryUiState>> = _categories.asStateFlow()

    init {
        viewModelScope.launch {
            taskRepository.getPlannedTasks("All").collect { taskEntities ->
                _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
            }
        }
        viewModelScope.launch {
            taskRepository.getAllCategories().collect { taskEntities ->
                _categories.value = taskEntities.map { it.toCategoryUiState() }
            }
        }
    }

    fun updateTaskList(category: String) {
        viewModelScope.launch {
            taskRepository.getPlannedTasks(category).collect { taskEntities ->
                _plannedTasks.value = taskEntities.map { it.toTaskUiState() }
            }

        }
        Log.d("searchResultTest", plannedTasks.value.toString())
    }

    //
    suspend fun insertAllTasks() {
        viewModelScope.launch {
            LocalTaskDataProvider.allTasks.forEach {
                taskRepository.insertTask(it.toTaskEntity())
            }
        }
    }

    suspend fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }

    suspend fun checkTask(taskUiState: TaskUiState) {
        viewModelScope.launch {
            taskRepository.updateTask(taskUiState.toTaskEntity().copy(checked = true))
        }
    }

}