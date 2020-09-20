package com.cardinalHealth.test.convertors

import androidx.room.TypeConverter
import com.cardinalHealth.test.dataModel.Album
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AlbumListConverter {

    private val gSon by lazy { Gson() }
    @TypeConverter
    fun albumListToStringObject(list:List<Album>):String{
        return gSon.toJson(list)
    }

    @TypeConverter
    fun stringToAlbumList(album:String):List<Album>{
        val typeToken = object: TypeToken<List<Album>>(){}.type
        return gSon.fromJson(album,typeToken)
    }
}