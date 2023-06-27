package ziqi.project.pursuingperfection.data

import kotlinx.coroutines.flow.Flow
import ziqi.project.pursuingperfection.database.TaskDao
import ziqi.project.pursuingperfection.database.TaskEntity
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllCategories(): Flow<List<TaskEntity>>{
        return taskDao.getAllCategories()
    }

    fun getPlannedTasks(category: String): Flow<List<TaskEntity>> {
        return if (category == "All") taskDao.getAllPlannedTasks()
        else taskDao.getPlannedTasks(category)
    }

    fun getCheckedTasks(category: String): Flow<List<TaskEntity>> {
        return if (category == "All") taskDao.getAllCheckedTasks()
        else taskDao.getCheckedTasks(category)
    }

    fun searchTaskByTitle(title: String, checked: Boolean): Flow<List<TaskEntity>> {
        return taskDao.searchTaskByTitle(title, checked)
    }

    fun searchTaskByContent(content: String, checked: Boolean): Flow<List<TaskEntity>> {
        return taskDao.searchTaskByContent(content, checked)
    }

    fun searchTask(searchInput: String, checked: Boolean): Flow<List<TaskEntity>> {
        return taskDao.searchTask(searchInput, checked)
    }

    fun getTaskById(id: Int): Flow<TaskEntity> {
        return taskDao.getTaskById(id)
    }

    fun getMostRecentTask(): Flow<TaskEntity>{
        return taskDao.getMostRecentTask()
    }

    suspend fun insertTask(taskEntity: TaskEntity): Long {
        return taskDao.insertTask(taskEntity)
    }

    suspend fun deleteTask(taskEntity: TaskEntity) {
        taskDao.deleteTask(taskEntity)
    }

    suspend fun deleteTaskById(id: Int){
        taskDao.deleteTaskById(id)
    }

    suspend fun deleteMostRecentTask(){
        taskDao.deleteMostRecentTask()
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    suspend fun updateTask(taskEntity: TaskEntity) {
        taskDao.updateTask(taskEntity)
    }



}