package ziqi.project.pursuingperfection.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("select * from categoryEntity")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("select * from categoryEntity where category = :categoryName")
    fun getCategory(categoryName: String): Flow<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryEntity: CategoryEntity)

//    @Delete
//    suspend fun removeCategory(categoryEntity: CategoryEntity)

    @Query("DELETE from categoryEntity WHERE category = :categoryName")
    suspend fun deleteCategory(categoryName: String)

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity)
}