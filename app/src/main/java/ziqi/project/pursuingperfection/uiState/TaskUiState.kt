package ziqi.project.pursuingperfection.uiState

import androidx.annotation.DrawableRes
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.database.TaskEntity
import ziqi.project.pursuingperfection.utils.Converters

data class TaskUiState(
    val id: Int = 0,
    val title: String = "Package Shipped",
    val contents: List<Item> = listOf(
        Item("Your Call of Duty disk has shipped, prepare to pick up.", true),
        Item("Estimated arrival time: 12:00 EST.", true),
        Item("While you may not at home, please let someone open the door.", false)
    ),
    @DrawableRes val profilePhoto: Int = R.drawable.ic_launcher_foreground,
    val category: String = "Google",
    val timeCreated: Int = 20,
    val checked: Boolean = false,
    val priority: String = "High",
    val lifeSpan: Int = 7,
    val lifeSpent: Int = 3
){
    fun asString(): String{
        return "id: $id, title: $title"
    }
}

data class Item(
    val content: String = "",
    val checked: Boolean = true
)

fun TaskUiState.toTaskEntity(): TaskEntity{
    return TaskEntity(
        this.id,
        this.title,
        Converters.listMyModelToJsonStr(this.contents),
        this.profilePhoto,
        this.category,
        this.timeCreated,
        this.checked,
        this.priority,
        this.lifeSpan,
        this.lifeSpent
    )
}
