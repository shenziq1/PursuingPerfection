package ziqi.project.pursuingperfection.database

import androidx.annotation.DrawableRes
import androidx.compose.ui.text.toLowerCase
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.uiState.SearchResultUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.utils.Converters


@Entity(tableName = "taskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val contents: String,
    @DrawableRes val profilePhoto: Int,
    val category: String,
    val timeCreated: Int,
    val checked: Boolean,
    val priority: String,
    val lifeSpan: Int,
    val lifeSpent: Int
)


fun TaskEntity.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        title = this.title,
        contents = Converters.jsonStrToListMyModel(this.contents) ?: listOf(Item()),
        profilePhoto = this.profilePhoto,
        category = this.category,
        timeCreated = this.timeCreated,
        checked = this.checked,
        priority = this.priority,
        lifeSpan = this.lifeSpan,
        lifeSpent = this.lifeSpent
    )
}

fun TaskEntity.toSearchResultUiState(search: String): SearchResultUiState {
    val contents = Converters.jsonStrToListMyModel(this.contents) ?: listOf(Item())
    val filteredContents =
        contents.filter { item: Item -> item.content.lowercase().contains(search.lowercase()) }
    val content =
        if (filteredContents.isEmpty()) contents[0].content else filteredContents[0].content
    return SearchResultUiState(
        id = this.id,
        title = this.title,
        content = content,
        profilePhoto = this.profilePhoto
    )
}