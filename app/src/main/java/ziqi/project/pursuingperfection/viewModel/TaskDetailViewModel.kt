package ziqi.project.pursuingperfection.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val id = savedStateHandle.get<Int>("id") ?: 0
}