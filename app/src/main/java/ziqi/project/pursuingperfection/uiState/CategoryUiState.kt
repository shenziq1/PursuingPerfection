package ziqi.project.pursuingperfection.uiState

import androidx.annotation.DrawableRes
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.database.CategoryEntity

data class CategoryUiState(
    val id: Int = 0,
    val category: String = "Default",
    @DrawableRes val profilePhoto: Int = R.drawable.ic_launcher_foreground
)

fun CategoryUiState.toCategoryEntity(): CategoryEntity{
    return CategoryEntity(
        id = this.id,
        category = this.category,
        profilePhoto = this.profilePhoto
    )
}