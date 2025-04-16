package com.ucb.framework.persistenceBook

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IBookDAO {

    @Query("SELECT * FROM favorite_book")
    fun getMyBooks(): List<BookDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: BookDB)

    @Query("DELETE FROM favorite_book")
    suspend fun deleteAll()
}