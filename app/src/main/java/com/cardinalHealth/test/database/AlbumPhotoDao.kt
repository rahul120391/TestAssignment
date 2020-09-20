package com.cardinalHealth.test.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardinalHealth.test.dataModel.Album
import com.cardinalHealth.test.dataModel.Photo
import com.cardinalHealth.test.database.DatabaseConstants.ALBUM_ID
import com.cardinalHealth.test.database.DatabaseConstants.ALBUM_TABLE
import com.cardinalHealth.test.database.DatabaseConstants.GALLERY_TABLE

@Dao
interface AlbumPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(list:List<Album>):Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhotos(list:List<Photo>):Array<Long>

    @Query("select * from $ALBUM_TABLE")
    fun getAllAlbums():List<Album>

    @Query("select * from $GALLERY_TABLE where $ALBUM_ID=:albumId")
    fun getAllPhotos(albumId:Int):List<Photo>
}