package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(category = "Nono", title = "Abc"),
        TaskUiState(category = "Nono", title = "Abc", priority = "Medium"),
        TaskUiState(category = "Nono", title = "Abc", priority = "Low"),
        TaskUiState(category = "Nono", title = "CBa"),
        TaskUiState(category = "blablabla", title = "Math")
    )
}