package ziqi.project.pursuingperfection.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {
    @Query("select * from taskEntity")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("select * from taskEntity where checked = 1 and category = :category")
    fun getCheckedTasks(category: String): Flow<List<TaskEntity>>

    @Query("select * from taskEntity where checked = 1")
    fun getAllCheckedTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE checked = 0 AND category = :category ")
    fun getPlannedTasks(category: String): Flow<List<TaskEntity>>

    @Query("select * from taskEntity where checked = 0")
    fun getAllPlannedTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE id = :id")
    fun getTaskById(id: Int): Flow<TaskEntity>

    @Insert
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("delete from taskEntity")
    suspend fun deleteAllTasks()

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)
}