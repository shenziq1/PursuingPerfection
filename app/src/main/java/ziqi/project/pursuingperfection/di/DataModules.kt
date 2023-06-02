package ziqi.project.pursuingperfection.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ziqi.project.pursuingperfection.data.TaskRepository
import ziqi.project.pursuingperfection.database.TaskDao
import ziqi.project.pursuingperfection.database.TaskDatabase
import ziqi.project.pursuingperfection.utils.Converters
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi{
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Provides
    fun provideRepository(taskDao: TaskDao): TaskRepository{
        return TaskRepository(taskDao = taskDao)
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, moshi: Moshi): TaskDatabase{
        Converters.initialize(moshi = moshi)
        return Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "TaskDatabase"
        ).build()
    }
}

