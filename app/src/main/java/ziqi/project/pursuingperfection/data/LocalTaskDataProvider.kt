package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(),
        TaskUiState(category = "Nono"),
        TaskUiState(category = "Nono", checked = true),
        TaskUiState(category = "blablabla")
    )
}