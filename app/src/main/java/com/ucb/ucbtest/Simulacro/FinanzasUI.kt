package com.ucb.ucbtest.Simulacro

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ucb.domain.Movie
import com.ucb.ucbtest.movie.MovieViewModel
import com.ucb.ucbtest.navigation.Screen


@Composable
fun FinanzasUI(viewModel: FinanzasViewModel = hiltViewModel(), navController: NavHostController,onSuccess : () -> Unit) {
//    val viewModel: FinanzasViewModel = viewModel()
    val total by viewModel.total.collectAsState()

    val context = LocalContext.current
    // Llamamos a calcularTotal cuando se carga la pantalla
    LaunchedEffect(Unit) {
        viewModel.calcularTotal()
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Finanzas Personales")
            Text("Total:")
            Text("$total")
            Button(
                onClick = {
                    Toast.makeText(context,"Cargando Registrar Ingresos",Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.FinanzasIngreso.route)
                }
            ) {
                Text("Registrar Ingreso")
            }

            Button(
                onClick = {
                    Toast.makeText(context,"Cargando Registrar Egresos",Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.FinanzasEgreso.route)
                }
            ) {
                Text("Registrar Egreso")
            }

            Button(
                onClick = {
                    Toast.makeText(context,"Cargando Lista de Registros",Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.FinanzasRegistros.route)
                }
            ) {
                Text("Ver Lista de Registros")
            }
        }
    }
}