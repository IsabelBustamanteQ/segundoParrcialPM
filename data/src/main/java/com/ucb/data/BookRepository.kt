package com.ucb.data

import com.ucb.data.books.IBookRemoteDataSource
import com.ucb.domain.books.Book

class BookRepository(
    val remoteDataSource: IBookRemoteDataSource
) {
    suspend fun findBooks(bookName: String): NetworkResult<List<Book>> {
        return this.remoteDataSource.fetchBooks(bookName)
    }
}