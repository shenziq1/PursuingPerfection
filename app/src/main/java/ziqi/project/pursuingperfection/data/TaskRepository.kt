package ziqi.project.pursuingperfection.data

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.flow.Flow
import ziqi.project.pursuingperfection.database.TaskDao
import ziqi.project.pursuingperfection.database.TaskEntity
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.uiState.toTaskEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(val taskDao: TaskDao) {
    val allTasks = LocalTaskDataProvider.allTasks
    fun getTasks(checked: Boolean, category: String): List<TaskUiState>{
        return if (checked){
            if (category == "All") LocalTaskDataProvider.allTasks.filter { it.checked }
            else LocalTaskDataProvider.allTasks.filter { it.category == category && it.checked }
        } else {
            if (category == "All") LocalTaskDataProvider.allTasks.filter { !it.checked }
            else LocalTaskDataProvider.allTasks.filter { it.category == category && !it.checked }
        }
    }

    fun getPlannedTasks(category: String): Flow<List<TaskEntity>>{
        return if (category == "All") taskDao.getAllPlannedTasks()
        else taskDao.getPlannedTasks(category)
    }

    fun getCheckedTasks(category: String): Flow<List<TaskEntity>>{
        return if (category == "All") taskDao.getAllCheckedTasks()
        else taskDao.getCheckedTasks(category)
    }

    fun getTaskById(id: Int): Flow<TaskEntity>{
        return taskDao.getTaskById(id)
    }

    suspend fun insertTask(taskEntity: TaskEntity){
        taskDao.insertTask(taskEntity)
    }
    suspend fun updateTask(taskEntity: TaskEntity){
        taskDao.updateTask(taskEntity)
    }
}