package com.cardinalHealth.test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cardinalHealth.test.convertors.AlbumListConverter
import com.cardinalHealth.test.convertors.PhotoListConverter
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.database.DatabaseConstants.DATABASE_VERSION

@Database(entities = [Album::class,Photo::class],exportSchema = false,version=DATABASE_VERSION)
@TypeConverters(AlbumListConverter::class,PhotoListConverter::class)
abstract class MyDatabase:RoomDatabase(){
    abstract fun getAlbumPhotoDao():AlbumPhotoDao
}