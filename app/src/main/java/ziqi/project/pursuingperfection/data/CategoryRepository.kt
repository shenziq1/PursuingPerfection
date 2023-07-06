package ziqi.project.pursuingperfection.data

import kotlinx.coroutines.flow.Flow
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

    suspend fun deleteCategory(categoryName: String){
        categoryDao.deleteCategory(categoryName)
    }

    suspend fun updateCategory(categoryEntity: CategoryEntity){
        categoryDao.updateCategory(categoryEntity)
    }
}