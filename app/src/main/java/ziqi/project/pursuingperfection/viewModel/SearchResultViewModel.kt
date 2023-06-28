package ziqi.project.pursuingperfection.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import ziqi.project.pursuingperfection.data.Home
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.toSearchResultUiState
import ziqi.project.pursuingperfection.uiState.SearchResultUiState
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _homeScreenSearchResult: MutableStateFlow<List<SearchResultUiState>> =
        MutableStateFlow(emptyList())
    val homePageSearchResult = _homeScreenSearchResult.asStateFlow()
    private var _doneScreenSearchResult: MutableStateFlow<List<SearchResultUiState>> =
        MutableStateFlow(emptyList())
    val donePageSearchResult = _doneScreenSearchResult.asStateFlow()

    fun updateSearchResult(route: String, searchInput: String) {
        if (route == Home.route) {
            viewModelScope.launch {
                taskRepository.searchTask(searchInput, false).filterNotNull()
                    .collect { taskEntities ->
                        _homeScreenSearchResult.value =
                            taskEntities.map { it.toSearchResultUiState(searchInput) }
                    }
            }
        } else {
            viewModelScope.launch {
                taskRepository.searchTask(searchInput, true).filterNotNull()
                    .collect { taskEntities ->
                        _doneScreenSearchResult.value =
                            taskEntities.map { it.toSearchResultUiState(searchInput) }
                    }
            }
        }
    }
    fun clearSearch() {
        _homeScreenSearchResult.value = listOf()
        _doneScreenSearchResult.value = listOf()
    }
}



