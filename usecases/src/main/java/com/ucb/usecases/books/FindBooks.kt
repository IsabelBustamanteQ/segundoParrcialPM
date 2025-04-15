package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.data.MovieRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Gitalias
import com.ucb.domain.books.Book

class FindBooks(
    val bookRepository: BookRepository,

    ) {
    suspend fun invoke(bookName: String) : NetworkResult<List<Book>> {
        return bookRepository.findBooks(bookName)
    }
}