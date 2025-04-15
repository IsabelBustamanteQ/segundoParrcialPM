package com.ucb.data.books

import com.ucb.data.NetworkResult
import com.ucb.domain.books.Book


interface IBookRemoteDataSource {
    suspend fun fetchBooks(bookName: String): NetworkResult<List<Book>>
}