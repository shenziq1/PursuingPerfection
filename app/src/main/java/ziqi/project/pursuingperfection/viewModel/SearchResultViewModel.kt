package ziqi.project.pursuingperfection.viewModel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.uiState.Item
import javax.inject.Inject

class SearchResultViewModel @Inject constructor(private val taskRepository: TaskRepository) :
    ViewModel() {
    private var _homeScreenSearchResult: MutableStateFlow<List<SearchResultUiState>> =
        MutableStateFlow(emptyList())
    val homePageSearchResult = _homeScreenSearchResult.asStateFlow()
    private var _doneScreenSearchResult: MutableStateFlow<List<SearchResultUiState>> =
        MutableStateFlow(emptyList())
    val donePageSearchResult = _doneScreenSearchResult.asStateFlow()

    fun updateSearchResult(route: String, searchInput: String){

    }

}

data class SearchResultUiState(
    val id: Int,
    val title: String,
    val content: Item,
    @DrawableRes val profilePhoto: Int,
)