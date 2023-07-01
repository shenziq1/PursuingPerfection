package ziqi.project.pursuingperfection.data

import androidx.compose.material.icons.Icons
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import ziqi.project.pursuingperfection.R
import ziqi.project.pursuingperfection.database.CategoryDao
import ziqi.project.pursuingperfection.database.CategoryEntity
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    fun getCurrentCategory(categoryName: String): Flow<CategoryEntity>{
        return categoryDao.getCategory(categoryName)
    }

    suspend fun addCategory(categoryEntity: CategoryEntity){
        categoryDao.addCategory(categoryEntity)
    }

    suspend fun removeCategory(categoryName: String){
        categoryDao.removeCategory(categoryName)
    }

    suspend fun replaceCategory(categoryEntity: CategoryEntity){
        categoryDao.replaceCategory(categoryEntity)
    }
}