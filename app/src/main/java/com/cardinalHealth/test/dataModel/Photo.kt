package com.cardinalHealth.test.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cardinalHealth.test.database.DatabaseConstants.ALBUM_ID
import com.cardinalHealth.test.database.DatabaseConstants.GALLERY_TABLE
import com.cardinalHealth.test.database.DatabaseConstants.ID
import com.cardinalHealth.test.database.DatabaseConstants.THUMBNAIL_URL
import com.cardinalHealth.test.database.DatabaseConstants.TITLE
import com.cardinalHealth.test.database.DatabaseConstants.URL

@Entity(tableName = GALLERY_TABLE)
data class Photo(
    @ColumnInfo(name = ALBUM_ID)
    val albumId: Int,
    @ColumnInfo(name = ID)
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = THUMBNAIL_URL)
    val thumbnailUrl: String,
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = URL)
    val url: String
)