package com.ucb.framework.books

import com.ucb.data.NetworkResult
import com.ucb.data.books.IBookRemoteDataSource
import com.ucb.domain.books.Book
import com.ucb.framework.mappers.toModel
import com.ucb.framework.service.RetrofitBuilder

class BookRemoteDataSource(
    val retrofiService: RetrofitBuilder
): IBookRemoteDataSource {
    override suspend fun fetchBooks(bookName: String): NetworkResult<List<Book>> {
        val response = retrofiService.bookService.fetchBooks(bookName)
        if (response.isSuccessful) {
            return NetworkResult.Success(response.body()!!.docs.map { it.toModel() })
        } else {
            return NetworkResult.Error(response.message())
        }
    }
}