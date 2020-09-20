package com.cardinalHealth.test.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cardinalHealth.test.database.DatabaseConstants.ALBUM_TABLE
import com.cardinalHealth.test.database.DatabaseConstants.ID
import com.cardinalHealth.test.database.DatabaseConstants.TITLE
import com.cardinalHealth.test.database.DatabaseConstants.USER_ID

@Entity(tableName = ALBUM_TABLE)
data class Album(
    @ColumnInfo(name = ID)
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = USER_ID)
    val userId: Int
)