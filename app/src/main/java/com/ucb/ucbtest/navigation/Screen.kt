package com.ucb.ucbtest.navigation

sealed class Screen(val route: String) {
    object GitaliasScreen : Screen("gitlab")
    object TakePhotoScreen: Screen("takephoto")
    object MenuScreen: Screen("menu")
    object LoginScreen: Screen("login")
    object MoviesScreen: Screen("movies")
    object MovieDetailScreen: Screen("movieDetail")
    object CounterScreen: Screen("counter")
    object FinanzasScreen:Screen("finanzas")
    object FinanzasRegistros:Screen("finanzasRegistros")
    object FinanzasIngreso:Screen("finanzasIngreso")
    object FinanzasEgreso:Screen("finanzasEgreso")

//    Books
    object BooksScreen:Screen("books")
    object FavoriteBooksScreen:Screen("favoriteBooks")
}