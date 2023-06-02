package ziqi.project.pursuingperfection.utils

import androidx.room.TypeConverters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ziqi.project.pursuingperfection.uiState.Item
import java.lang.reflect.ParameterizedType

object Converters {
    private lateinit var moshi: Moshi
    private lateinit var listMyData: ParameterizedType
    private lateinit var jsonAdapter: JsonAdapter<List<Item>>

    fun initialize(moshi: Moshi){
        this.moshi = moshi
        listMyData = Types.newParameterizedType(List::class.java, Item::class.java)
        jsonAdapter = Converters.moshi.adapter(listMyData)
    }

    @TypeConverters
    fun listMyModelToJsonStr(listMyModel: List<Item>): String {
        return jsonAdapter.toJson(listMyModel)
    }

    @TypeConverters
    fun jsonStrToListMyModel(jsonStr: String): List<Item>? {
        return jsonAdapter.fromJson(jsonStr)
    }
}