package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState

object LocalTaskDataProvider {
    val allTasks = listOf(
        TaskUiState(checked = true),
        TaskUiState(category = "Nono", title = "Abc"),
        TaskUiState(category = "Nono", title = "CBa",checked = true),
        TaskUiState(category = "blablabla", title = "Math")
    )
}