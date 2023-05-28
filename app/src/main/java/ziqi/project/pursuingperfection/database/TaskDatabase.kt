package ziqi.project.pursuingperfection.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ziqi.project.pursuingperfection.uiState.TaskUiState

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}