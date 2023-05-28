package ziqi.project.pursuingperfection.database

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import ziqi.project.pursuingperfection.uiState.TaskUiState

@Entity(tableName = "taskEntity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    //val content: List<Pair<String, Boolean>>,
    @DrawableRes val profilePhoto: Int,
    val category: String,
    val timeCreated: Int,
    val checked: Boolean,
    val priority: String,
    val lifeSpan: Int,
    val lifeSpent: Int
)

fun TaskEntity.taskEntityToTaskUiState(): TaskUiState{
    return TaskUiState(
        this.id,
        this.title,
        //this.content,
        listOf(),
        this.profilePhoto,
        this.category,
        this.timeCreated,
        this.checked,
        this.priority,
        this.lifeSpan,
        this.lifeSpent
    )
}