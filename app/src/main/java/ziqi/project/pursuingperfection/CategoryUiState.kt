package ziqi.project.pursuingperfection

import androidx.annotation.DrawableRes

data class CategoryUiState(
    val name: String = "All",
    @DrawableRes val picture: Int = R.drawable.ic_launcher_foreground
)