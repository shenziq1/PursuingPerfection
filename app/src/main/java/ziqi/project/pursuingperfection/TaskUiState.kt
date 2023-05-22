package ziqi.project.pursuingperfection

import androidx.annotation.DrawableRes

data class TaskUiState(
    val title: String = "Package Shipped",
    val content: List<Pair<String, Boolean>> = listOf(
        "Your Call of Duty disk has shipped, prepare to pick up." to true,
        "Estimated arrival time: 12:00 EST." to true,
        "While you may not at home, please let someone open the door." to false),
    @DrawableRes val profilePhoto: Int = R.drawable.ic_launcher_foreground,
    val userName: String = "Google",
    val timeCreated: Int = 20,
    val checked: Boolean = false
)
