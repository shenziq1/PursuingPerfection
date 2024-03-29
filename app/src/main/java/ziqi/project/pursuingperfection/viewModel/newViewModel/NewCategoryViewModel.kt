package ziqi.project.pursuingperfection.viewModel.newViewModel

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.data.CategoryRepository
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toCategoryUiState
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.toCategoryEntity
import javax.inject.Inject

@HiltViewModel
class NewCategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
    val category = savedStateHandle.get<String>("category") ?: "new"
    val type = savedStateHandle.get<String>("type") ?: "new"

    private var _listUiState = MutableStateFlow(listOf(CategoryUiState()))
    val listUiState = _listUiState.asStateFlow()

    private var _currentUiState = MutableStateFlow(CategoryUiState())
    val currentUiState = _currentUiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getAllCategories().filterNotNull().collect { categoryEntities ->
                _listUiState.value = categoryEntities.map { it.toCategoryUiState() }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.getCurrentCategory(category).filterNotNull().collect {
                _currentUiState.value = it.toCategoryUiState()
            }
        }
    }

    suspend fun updateCategory(
        newCategory: String,
        newIcon: Int = R.drawable.ic_launcher_foreground
    ) {
        _currentUiState.value = _currentUiState.value.copy(category = newCategory, profilePhoto = newIcon)
        viewModelScope.launch(Dispatchers.IO) {
            taskRepository.updateTaskCategory(category, newCategory, newIcon)
        }
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.updateCategory(
                _currentUiState.value.toCategoryEntity()
            )
        }
    }

    suspend fun addCategory(
        newCategory: String,
        newIcon: Int = R.drawable.ic_launcher_foreground
    ): Boolean {
        return if (newCategory !in _listUiState.value.map { it.category }){
            viewModelScope.launch(Dispatchers.IO) {
                categoryRepository.addCategory(
                    CategoryUiState(
                        0,
                        newCategory,
                        newIcon
                    ).toCategoryEntity()
                )
            }
            true
        } else {
            false
        }
    }

    suspend fun removeCategory(category: String) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
        viewModelScope.launch {
            taskRepository.deleteTaskByCategory(category)
        }
    }
}