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

    @Query("select * from taskEntity where checked = 0 and category = :category ")
    fun getPlannedTasks(category: String): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)
}