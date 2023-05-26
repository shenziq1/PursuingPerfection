package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(),
        TaskUiState(name = "Nono"),
        TaskUiState(name = "Nono", checked = true),
        TaskUiState(name = "blablabla")
    )
}