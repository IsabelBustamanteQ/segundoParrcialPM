package com.ucb.framework.mappers

import com.ucb.domain.Gitalias
import com.ucb.domain.Movie
import com.ucb.domain.books.Book
import com.ucb.domain.simulacro.Finanza
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.BookDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.persistence.GitAccount
import com.ucb.framework.persistenceBook.BookDB
import com.ucb.framework.simulacroPersistence.FinanzasRegistro

fun AvatarResponseDto.toModel(): Gitalias {
    return Gitalias(
        login = login,
        avatarUrl = url
    )
}

fun Gitalias.toEntity(): GitAccount {
    return GitAccount(login)
}

fun GitAccount.toModel(): Gitalias {
    return Gitalias(
        alias,
        ""
    )
}

fun MovieDto.toModel(): Movie {
    return Movie(
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}

//Simulacro
fun Finanza.toEntity(): FinanzasRegistro {
    return FinanzasRegistro(nombre, precio, descripcion, fecha)
}
fun FinanzasRegistro.toModel(): Finanza {
    return Finanza(nombre=nombre, precio=precio, descripcion=descripcion, fecha=fecha)
}

//Books

fun BookDto.toModel(): Book {
    return Book(
        author = author_name ?: listOf("Autor desconocido"),
        title = title ?: "Sin título",
        publish_year = first_publish_year ?: "Año desconocido"
    )
}
fun Book.toEntity(): BookDB {
    return BookDB(
        author = author.ifEmpty { listOf("Autor desconocido") },
        title = title.ifBlank { "Sin título" },
        publish_year = publish_year.ifBlank { "Año desconocido" }
    )
}

fun BookDB.toModel(): Book {
    return Book(author=author,title=title,publish_year=publish_year)
}
