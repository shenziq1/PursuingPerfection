package ziqi.project.pursuingperfection.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ziqi.project.pursuingperfection.uiState.Item
import java.lang.reflect.ParameterizedType

object Converters {
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val listMyData : ParameterizedType = Types.newParameterizedType(List::class.java, Item::class.java)
    private val jsonAdapter: JsonAdapter<List<Item>> = moshi.adapter(listMyData)

    @TypeConverters
    fun listMyModelToJsonStr(listMyModel: List<Item>): String {
        return jsonAdapter.toJson(listMyModel)
    }

    @TypeConverters
    fun jsonStrToListMyModel(jsonStr: String): List<Item>? {
        return jsonAdapter.fromJson(jsonStr)
    }
}