package ziqi.project.pursuingperfection.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ziqi.project.pursuingperfection.uiState.TaskUiState
import ziqi.project.pursuingperfection.utils.Converters

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}