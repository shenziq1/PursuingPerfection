package ziqi.project.pursuingperfection.uiState

import androidx.annotation.DrawableRes

data class SearchResultUiState(
    val id: Int,
    val title: String,
    val content: String,
    @DrawableRes val profilePhoto: Int,
)