package ziqi.project.pursuingperfection.uiState

import androidx.annotation.DrawableRes
import ziqi.project.pursuingperfection.R

data class CategoryUiState(
    val name: String = "All",
    @DrawableRes val picture: Int = R.drawable.ic_launcher_foreground
)