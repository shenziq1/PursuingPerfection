package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(category = "Nono", title = "A"),
        TaskUiState(category = "Nono", title = "B", priority = 1),
        TaskUiState(category = "Nono", title = "C", priority = 2),
        TaskUiState(category = "blablabla", title = "D"),
        TaskUiState(category = "blablabla", title = "E")
    )
}