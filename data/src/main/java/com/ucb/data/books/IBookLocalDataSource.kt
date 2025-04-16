package com.ucb.data.books

import com.ucb.domain.books.Book

interface IBookLocalDataSource {
    suspend fun save(book: Book): Boolean
    suspend fun getBooks():List<Book>

}