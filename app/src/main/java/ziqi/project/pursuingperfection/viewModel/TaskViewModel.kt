package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ziqi.project.pursuingperfection.data.LocalTaskDataProvider
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.uiState.TaskUiState
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {
    private var _tasks = MutableStateFlow<List<TaskUiState>>(emptyList())
    val tasks = _tasks.asStateFlow()

    init {
        _tasks.value = taskRepository.getTasks(checked = false, category = "All")
    }
}