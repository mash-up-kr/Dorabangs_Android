package com.mashup.dorabangs.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.mashup.dorabangs.data.model.LinkKeywordResponseModel

class ListTypeConverters {
    @TypeConverter
    fun listToJson(value: List<LinkKeywordResponseModel>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<LinkKeywordResponseModel>? {
        return Gson().fromJson(value, Array<LinkKeywordResponseModel>::class.java)?.toList()
    }
}
