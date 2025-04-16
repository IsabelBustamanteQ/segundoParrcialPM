package com.ucb.usecases.books

import com.ucb.data.BookRepository
import com.ucb.domain.books.Book

class SaveBook(val repository: BookRepository) {
    suspend fun invoke(book: Book):Boolean{
        repository.save(book)
        return true
    }
}
