package com.cardinalHealth.test.convertors

import androidx.room.TypeConverter
import com.cardinalHealth.test.dataModel.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PhotoListConverter {

    private val gSon by lazy { Gson() }
    @TypeConverter
    fun photoListToStringObject(list:List<Photo>):String{
        return gSon.toJson(list)
    }

    @TypeConverter
    fun stringToPhotoListObject(album:String):List<Photo>{
        val typeToken = object: TypeToken<List<Photo>>(){}.type
        return gSon.fromJson(album,typeToken)
    }
}