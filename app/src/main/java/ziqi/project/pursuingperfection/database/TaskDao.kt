package ziqi.project.pursuingperfection.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapInfo
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

    @Query("SELECT * FROM taskEntity ORDER BY id DESC LIMIT 1")
    fun getMostRecentTask(): Flow<TaskEntity>

    //@MapInfo(keyColumn = "profilePhoto")
    @Query("SELECT * FROM taskEntity GROUP BY category")
    fun getAllCategories(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE title LIKE '%' || :title || '%' AND checked = :checked")
    fun searchTaskByTitle(title: String, checked: Boolean): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE contents LIKE '%' || :content || '%' AND checked = :checked")
    fun searchTaskByContent(content: String, checked: Boolean): Flow<List<TaskEntity>>

    @Query("SELECT * FROM taskEntity WHERE (contents LIKE '%' || :searchInput || '%' OR title LIKE '%' || :searchInput || '%') AND checked = :checked")
    fun searchTask(searchInput: String, checked: Boolean): Flow<List<TaskEntity>>

    @Insert
    suspend fun insertTask(taskEntity: TaskEntity): Long

    @Delete
    suspend fun deleteTask(taskEntity: TaskEntity)

    @Query("DELETE FROM taskEntity WHERE id = :id")
    suspend fun deleteTaskById(id: Int)

    @Query("DELETE FROM taskEntity")
    suspend fun deleteAllTasks()

    @Query("DELETE FROM taskEntity WHERE id IN (SELECT id FROM taskEntity ORDER BY id DESC LIMIT 1)")
    suspend fun deleteMostRecentTask()

    @Update
    suspend fun updateTask(taskEntity: TaskEntity)

    @Query("UPDATE taskEntity SET category = :newCategory, profilePhoto = :profilePhoto WHERE category = :oldCategory")
    suspend fun updateTaskCategory(oldCategory: String, newCategory: String, profilePhoto: Int)

    @Query("DELETE FROM taskEntity WHERE category = :category")
    suspend fun deleteTaskByCategory(category: String)

}