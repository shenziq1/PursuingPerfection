package ziqi.project.pursuingperfection.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ziqi.project.pursuingperfection.data.CategoryRepository
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.CategoryDao
import ziqi.project.pursuingperfection.database.CategoryDatabase
import ziqi.project.pursuingperfection.database.TaskDao
import ziqi.project.pursuingperfection.database.TaskDatabase
import ziqi.project.pursuingperfection.utils.Converters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object TaskRepositoryModule {

    @Singleton
    @Provides
    fun provideTaskDatabase(@ApplicationContext context: Context, moshi: Moshi): TaskDatabase {
        Converters.initialize(moshi = moshi)
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "TaskDatabase"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao = taskDao)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object CategoryRepositoryModule {
    @Singleton
    @Provides
    fun provideCategoryDatabase(@ApplicationContext context: Context): CategoryDatabase {
        return Room.databaseBuilder(
            context,
            CategoryDatabase::class.java,
            "CategoryDatabase"
        ).createFromAsset("database/CategoryDatabase.db")
            .build()
    }



    @Provides
    fun provideCategoryDao(database: CategoryDatabase): CategoryDao {
        return database.categoryDao()
    }
    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepository(categoryDao = categoryDao)
    }
}

