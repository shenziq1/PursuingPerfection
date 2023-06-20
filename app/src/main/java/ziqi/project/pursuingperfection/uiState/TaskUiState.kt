package ziqi.project.pursuingperfection.uiState

import androidx.annotation.DrawableRes
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.database.TaskEntity
import ziqi.project.pursuingperfection.utils.Converters
import java.time.LocalDateTime

data class TaskUiState(
    val id: Int = 0,
    val title: String = "Package Shipped",
    val contents: List<Item> = listOf(
        Item(0,"Your Call of Duty disk has shipped, prepare to pick up.", true),
        Item(1, "Estimated arrival time: 12:00 EST.", true),
        Item(2,"While you may not at home, please let someone open the door.", false)
    ),
    @DrawableRes val profilePhoto: Int = R.drawable.ic_launcher_foreground,
    val category: String = "Google",
    val priority: Int = 0,
    val timeStart: LocalDateTime = LocalDateTime.now(),
    val timeEnd: LocalDateTime = LocalDateTime.now().plusHours(30)
){
    val checked = contents.map { it.checked }.all { it }
}

data class Item(
    val id: Int = 0,
    val content: String = "",
    val checked: Boolean = false
)

fun TaskUiState.toTaskEntity(): TaskEntity{
    return TaskEntity(
        this.id,
        this.title,
        Converters.listMyModelToJsonStr(this.contents),
        this.profilePhoto,
        this.category,
        this.checked,
        this.priority,
        this.timeStart.toString(),
        this.timeEnd.toString()
    )
}
