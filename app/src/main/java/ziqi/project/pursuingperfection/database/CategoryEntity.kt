package ziqi.project.pursuingperfection.database

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import ziqi.project.pursuingperfection.uiState.CategoryUiState

@Entity(tableName = "categoryEntity")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @DrawableRes val profilePhoto: Int,
    val category: String
)

fun CategoryEntity.toCategoryUiState(): CategoryUiState{
    return CategoryUiState(
        id = this.id,
        category = this.category,
        profilePhoto = this.profilePhoto
    )
}