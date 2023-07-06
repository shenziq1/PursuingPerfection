package ziqi.project.pursuingperfection.viewModel.currentViewModel

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.data.CategoryRepository
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.CategoryEntity
import ziqi.project.pursuingperfection.database.toCategoryUiState
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

@HiltViewModel
class CurrentCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    val category = savedStateHandle.get<String>("category") ?: "Default"
    private var _taskUiState = MutableStateFlow(TaskUiState())
    val taskUiState = _taskUiState.asStateFlow()
    private var _categoryUiState = MutableStateFlow(listOf(CategoryUiState()))
    val categoryUiState = _categoryUiState.asStateFlow()

    private val type = savedStateHandle.get<String>("type") ?: "edit"

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        when (type) {
            "edit" -> {
                viewModelScope.launch {
                    taskRepository.getTaskById(id).filterNotNull().collect {
                        _taskUiState.value = it.toTaskUiState()
                    }
                }
            }

            "new" -> {
                val newTaskUiState = TaskUiState()
                viewModelScope.launch {
                    val id = taskRepository.insertTask(newTaskUiState.toTaskEntity())
                    _taskUiState.value = _taskUiState.value.copy(id = id.toInt())
                }
//                viewModelScope.launch {
//                    categoryRepository.addCategory(
//                        CategoryEntity(
//                            1,
//                            R.drawable.ic_launcher_foreground,
//                            "Default"
//                        )
//                    )
//                }
                viewModelScope.launch {
                    categoryRepository.getAllCategories().filterNotNull()
                        .collect() { categoryEntities ->
                            _categoryUiState.value = categoryEntities.map {
                                it.toCategoryUiState()
                            }
                        }
                }
            }
        }
    }

    fun updateNewTaskCategory(categoryUiState: CategoryUiState) {
        _taskUiState.value = _taskUiState.value.copy(
            category = categoryUiState.category,
            profilePhoto = categoryUiState.profilePhoto
        )
    }

    suspend fun updateTaskToRepository() {
        viewModelScope.launch {
            taskRepository.updateTask(_taskUiState.value.toTaskEntity())
        }
    }
}