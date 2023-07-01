package ziqi.project.pursuingperfection.database

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import ziqi.project.pursuingperfection.uiState.CategoryUiState
import ziqi.project.pursuingperfection.uiState.Item
import ziqi.project.pursuingperfection.uiState.SearchResultUiState
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.utils.Converters
import java.time.LocalDateTime


@Entity(tableName = "taskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val contents: String,
    @DrawableRes val profilePhoto: Int,
    val category: String,
    val checked: Boolean,
    val priority: Int,
    val timeStart: String,
    val timeEnd: String
)


fun TaskEntity.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        title = this.title,
        contents = Converters.jsonStrToListMyModel(this.contents) ?: listOf(),
        profilePhoto = this.profilePhoto,
        category = this.category,
        priority = this.priority,
        timeStart = LocalDateTime.parse(timeStart),
        timeEnd = LocalDateTime.parse(timeEnd)
    )
}

fun TaskEntity.toCategoryUiState(): CategoryUiState {
    return CategoryUiState(
        id = this.id,
        category = this.category,
        profilePhoto = this.profilePhoto
    )
}

fun TaskEntity.toSearchResultUiState(search: String): SearchResultUiState {
    val contents = Converters.jsonStrToListMyModel(this.contents) ?: listOf()
    val filteredContents =
        contents.filter { item: Item -> item.content.lowercase().contains(search.lowercase()) }
    val content =
        if (filteredContents.isEmpty()) {
            if (contents.isEmpty()){
                ""
            } else {
                contents[0].content
            }
        } else {
            filteredContents[0].content
        }
    return SearchResultUiState(
        id = this.id,
        title = this.title,
        content = content,
        profilePhoto = this.profilePhoto
    )
}