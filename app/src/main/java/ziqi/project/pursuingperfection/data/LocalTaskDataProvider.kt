package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(category = "Nono", title = "Abc"),
        TaskUiState(category = "Nono", title = "Abc", priority = 1),
        TaskUiState(category = "Nono", title = "Abc", priority = 2),
        TaskUiState(category = "Nono", title = "CBa"),
        TaskUiState(category = "blablabla", title = "Math")
    )
}