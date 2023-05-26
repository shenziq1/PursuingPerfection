package ziqi.project.pursuingperfection

object LocalTaskDataProvider {
    private val allTasks = listOf(
        TaskUiState(),
        TaskUiState(name = "Nono"),
        TaskUiState(name = "Nono", checked = true),
        TaskUiState(name = "blablabla")
    )

    fun getPlannedTasksByCategory(category: String): List<TaskUiState>{
        return if (category == "All") allTasks.filter { !it.checked }
        else allTasks.filter { it.name == category && !it.checked }
    }

    fun getCheckedTasksByCategory(category: String): List<TaskUiState>{
        return if (category == "All") allTasks.filter { it.checked }
        else allTasks.filter { it.name == category && it.checked }
    }
}