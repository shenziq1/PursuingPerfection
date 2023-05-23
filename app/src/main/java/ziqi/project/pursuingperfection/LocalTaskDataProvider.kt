package ziqi.project.pursuingperfection

object LocalTaskDataProvider {
    private val allTasks = listOf(
        TaskUiState(),
        TaskUiState(name = "Nono"),
        TaskUiState(name = "Nono"),
        TaskUiState(name = "blablabla")
    )

    fun getTasksByCategory(category: String): List<TaskUiState>{
        return if (category == "All") allTasks
        else allTasks.filter { it.name == category }
    }
}