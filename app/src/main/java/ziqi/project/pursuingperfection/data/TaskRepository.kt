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

    suspend fun deleteTask(taskEntity: TaskEntity){
        taskDao.deleteTask(taskEntity)
    }

    suspend fun deleteAllTasks(){
        taskDao.deleteAllTasks()
    }

    suspend fun updateTask(taskEntity: TaskEntity){
        taskDao.updateTask(taskEntity)
    }

}