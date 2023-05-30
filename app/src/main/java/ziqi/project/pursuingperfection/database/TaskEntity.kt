package ziqi.project.pursuingperfection.database

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ziqi.project.pursuingperfection.uiState.Item
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


fun TaskEntity.toTaskUiState(): TaskUiState{
    return TaskUiState(
        this.id,
        this.title,
        Converters.jsonStrToListMyModel(this.contents)?: listOf(Item()),
        this.profilePhoto,
        this.category,
        this.timeCreated,
        this.checked,
        this.priority,
        this.lifeSpan,
        this.lifeSpent
    )
}