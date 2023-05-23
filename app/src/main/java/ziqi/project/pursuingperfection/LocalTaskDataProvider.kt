package ziqi.project.pursuingperfection

object LocalTaskDataProvider {
    private val allTasks = listOf(
        TaskUiState(),
        TaskUiState(name = "Sasa"),
        TaskUiState(name = "Sasa"),
        TaskUiState(name = "blablabla")
    )

    fun getTasksByCategory(category: String): List<TaskUiState>{
        return if (category == "All") allTasks
        else allTasks.filter { it.name == category }
    }
}