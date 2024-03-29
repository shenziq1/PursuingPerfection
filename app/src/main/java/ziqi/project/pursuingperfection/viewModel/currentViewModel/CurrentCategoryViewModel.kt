package ziqi.project.pursuingperfection.viewModel.currentViewModel

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.CategoryRepository
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.CategoryEntity
import ziqi.project.pursuingperfection.database.toCategoryUiState
import ziqi.project.pursuingperfection.database.toTaskUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toCategoryEntity
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

    private var initializeCalled1 = false
    private var initializeCalled2 = false

    @MainThread
    fun initialize1() {
        if (initializeCalled1) return
        initializeCalled1 = true

        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getAllCategories().filterNotNull()
                .collect() { categoryEntities ->
                    _categoryUiState.value = categoryEntities.map { it.toCategoryUiState() }
                    Log.d("initialize1", _categoryUiState.value.toString())
                }
        }
        //Log.d("initialize1", _categoryUiState.value.toString())
    }

    @MainThread
    suspend fun initialize2(selectedCategory: String) {
        if (initializeCalled2) return
        initializeCalled2 = true

        val categories = viewModelScope.async(Dispatchers.IO) {
            categoryRepository.getAllCategoriesForOnce()
        }
        val categoryResult = categories.await()

        when (type) {
            "edit" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.getTaskById(id).filterNotNull().collect {
                        _taskUiState.value = it.toTaskUiState()
                    }
                }
            }

            "new" -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val newTask = if (categoryResult.isNotEmpty()) TaskUiState(
                        category = categoryResult.find { it.category == selectedCategory }?.category
                            ?: categoryResult.first().category,
                        profilePhoto = categoryResult.find { it.category == selectedCategory }?.profilePhoto
                            ?: categoryResult.first().profilePhoto
                    ) else TaskUiState()
                    taskRepository.insertTask(
                        newTask.toTaskEntity()
                    )
                    if (categoryResult.isEmpty())
                        categoryRepository.addCategory(CategoryUiState().toCategoryEntity())
                }
                viewModelScope.launch(Dispatchers.IO) {
                    taskRepository.getMostRecentTask().filterNotNull().collect() {
                        _taskUiState.value = it.toTaskUiState()
                    }
                }
            }
        }
    }

    suspend fun cancelTask() {
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.deleteMostRecentTask()
        }
    }

    suspend fun saveTaskToRepository(categoryUiState: CategoryUiState) {
        _taskUiState.value = _taskUiState.value.copy(
            category = categoryUiState.category,
            profilePhoto = categoryUiState.profilePhoto
        )
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTask(_taskUiState.value.toTaskEntity())
        }
    }
}