package ziqi.project.pursuingperfection.viewModel.currentViewModel

import android.util.Log
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
import ziqi.project.pursuingperfection.data.CategoryRepository
import ziqi.project.pursuingperfection.data.TaskRepository
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

        viewModelScope.launch {
            categoryRepository.getAllCategories().filterNotNull()
                .collect() { categoryEntities ->
                    _categoryUiState.value = categoryEntities.map {
                        it.toCategoryUiState()
                    }
                }
        }

        when (type) {
            "edit" -> {
                viewModelScope.launch {
                    taskRepository.getTaskById(id).filterNotNull().collect {
                        _taskUiState.value = it.toTaskUiState()
                    }
                }
            }

            "new" -> {
                viewModelScope.launch {
                    val id =
                        taskRepository.insertTask(
                            TaskUiState(
                                category = "FFF",
                                profilePhoto = _categoryUiState.value.last().profilePhoto
                            ).toTaskEntity()
                        )

                }
                viewModelScope.launch {
                    taskRepository.getMostRecentTask().filterNotNull().collect() {
                        _taskUiState.value = it.toTaskUiState()
                    }

                }
                Log.d("whyyyyyy", _categoryUiState.value.last().category)
                Log.d("whyyyyyy", _taskUiState.value.toString())
                Log.d("whyyyyyy", type)
            }
        }

    }

//    fun updateNewTaskCategoryWithDefault() {
//        _taskUiState.value = _taskUiState.value.copy(
//            category = _categoryUiState.value.last().category,
//            profilePhoto = _categoryUiState.value.last().profilePhoto,
//        )
//    }

    suspend fun saveTaskToRepository(categoryUiState: CategoryUiState) {
        _taskUiState.value = _taskUiState.value.copy(
            category = categoryUiState.category,
            profilePhoto = categoryUiState.profilePhoto
        )
        viewModelScope.launch {
            taskRepository.updateTask(_taskUiState.value.toTaskEntity())
        }
    }
}