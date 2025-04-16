package com.ucb.ucbtest.books

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ucb.ucbtest.R
import com.ucb.ucbtest.navigation.Screen

@Composable
fun BooksUI(
    viewModel: BookViewModel = hiltViewModel(),
    navController: NavHostController,
    onSuccess: () -> Unit
) {
    var bookName by remember { mutableStateOf("") }
    val bookState by viewModel.flow.collectAsState()
    val context = LocalContext.current
    val loadingMessage=stringResource(id = R.string.loading_favorite_books)
    val addingMessage=stringResource(R.string.adding_book)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.books_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = {
                    Toast.makeText(context, loadingMessage, Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.FavoriteBooksScreen.route)
                }
            ) {
                Text(stringResource(id=R.string.favorite_books))
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                value = bookName,
                onValueChange = { bookName = it },
                label = { Text(stringResource(id = R.string.book_name_input)) },
                singleLine = true
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = { viewModel.fetchBooks(bookName) }
            ) {
                Text(stringResource(id = R.string.gitalias_btn_find))
            }

            when (val state = bookState) {
                is BookViewModel.BookState.Init -> {
                    Text(stringResource(R.string.no_book_yet), modifier = Modifier.padding(top = 24.dp))
                }

                is BookViewModel.BookState.Successful -> {
                    Text(
                        text = stringResource(R.string.book_results),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 24.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    ) {
                        items(state.list) { book ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text("📖 ${book.title}", style = MaterialTheme.typography.titleLarge)
                                    Text("👤 "+stringResource(R.string.author_label)+ " ${book.author.joinToString()}")
                                    Text("📅 "+stringResource(R.string.publish_year_label)+" ${book.publish_year}")

                                    OutlinedButton(
                                        onClick = { viewModel.savingBook(book)
                                            Toast.makeText(context,addingMessage,Toast.LENGTH_SHORT).show()
                                                  },
                                        modifier = Modifier
                                            .padding(top = 12.dp)
                                            .size(48.dp),
                                        shape = RoundedCornerShape(24.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = stringResource(R.string.like),
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                is BookViewModel.BookState.Error -> {
                    Text("❌ ${state.message}", color = Color.Red, modifier = Modifier.padding(top = 24.dp))
                }

                is BookViewModel.BookState.Saved -> {
//
                }
            }
        }
    }
}
