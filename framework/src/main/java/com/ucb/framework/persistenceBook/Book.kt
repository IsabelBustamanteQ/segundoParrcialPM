package com.ucb.framework.persistenceBook

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity( tableName = "favorite_book")
data class BookDB(
    @ColumnInfo(name = "author")
    var author: List<String>,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "publish_year")
    var publish_year: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @RequiresApi(Build.VERSION_CODES.O)
    @ColumnInfo(name = "create_at")
    var createAt: String = LocalDateTime.now().toString()
}