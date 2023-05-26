package ziqi.project.pursuingperfection.data

import ziqi.project.pursuingperfection.uiState.TaskUiState
import javax.inject.Inject

class TaskRepository @Inject constructor() {
    val allTasks = LocalTaskDataProvider.allTasks
    fun getTasks(checked: Boolean, category: String): List<TaskUiState>{
        return if (checked){
            if (category == "All") LocalTaskDataProvider.allTasks.filter { it.checked }
            else LocalTaskDataProvider.allTasks.filter { it.name == category && it.checked }
        } else {
            if (category == "All") LocalTaskDataProvider.allTasks.filter { !it.checked }
            else LocalTaskDataProvider.allTasks.filter { it.name == category && !it.checked }
        }
    }
}