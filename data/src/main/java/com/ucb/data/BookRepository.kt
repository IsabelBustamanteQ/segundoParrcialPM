package com.ucb.data

import com.ucb.data.books.IBookLocalDataSource
import com.ucb.data.books.IBookRemoteDataSource
import com.ucb.domain.books.Book

class BookRepository(
    val remoteDataSource: IBookRemoteDataSource,
    val localDataSource: IBookLocalDataSource
) {
    suspend fun findBooks(bookName: String): NetworkResult<List<Book>> {
        return this.remoteDataSource.fetchBooks(bookName)
    }
    suspend fun save(book:Book):Boolean{
        this.localDataSource.save(book)
        return true
    }
    suspend fun getBooks():List<Book>{
        return this.localDataSource.getBooks()
    }
}