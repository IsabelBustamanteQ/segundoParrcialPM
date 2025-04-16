package com.ucb.framework.books

import android.content.Context
import com.ucb.data.books.IBookLocalDataSource
import com.ucb.domain.books.Book
import com.ucb.framework.mappers.toEntity
import com.ucb.framework.persistenceBook.BookRoomDatabase
import com.ucb.framework.persistenceBook.IBookDAO

class BookLocalDataSource(val context: Context) : IBookLocalDataSource {
    val bookDAO: IBookDAO = BookRoomDatabase.getDatabase(context).bookDao()
    override suspend fun save(book: Book): Boolean {
        bookDAO.insert(book.toEntity())
        return true
    }
}