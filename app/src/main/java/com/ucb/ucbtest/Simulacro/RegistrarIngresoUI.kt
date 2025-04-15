package com.ucb.ucbtest.Simulacro

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ucb.ucbtest.R
import com.ucb.ucbtest.gitalias.GitaliasViewModel
import com.ucb.ucbtest.login.LoginViewModel

@Composable
fun registrarIngresoUI(viewModel: FinanzasViewModel = hiltViewModel(),navController: NavHostController, onBackPressed: () -> Unit) {
//    val viewModel: FinanzasViewModel = viewModel()

    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    var context = LocalContext.current
//    val viewModel: FinanzasViewModel = hiltViewModel()

    val ingresoState=viewModel.finanzasState.collectAsState(FinanzasViewModel.FinanzasState.Init)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Ingresa datos del Ingreso"
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nombre,
                onValueChange = {
                    nombre = it
                },
                label = {
                    Text("ingresa el nombre")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = precio,
                onValueChange = {
                    precio = it
                },
                label = {
                    Text("ingresa el precio")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = descripcion,
                onValueChange = {
                    descripcion = it
                },
                label = {
                    Text("ingresa la descripcion")
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = fecha,
                onValueChange = {
                    fecha = it
                },
                label = {
                    Text("ingresa la fecha")
                }
            )
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.doIngreso(nombre,precio.toDouble(),descripcion,fecha)
                }
            ) {
                Text("Registrar")
            }
            when( val state = ingresoState.value) {
                is FinanzasViewModel.FinanzasState.Init, is FinanzasViewModel.FinanzasState.Loaded-> {
//                    Toast.makeText(context, "Init Ingresos", Toast.LENGTH_LONG).show()
                }
                is FinanzasViewModel.FinanzasState.Loading-> {
                    Toast.makeText(context, "Loading Ingresos", Toast.LENGTH_SHORT)
                }
                is FinanzasViewModel.FinanzasState.Successful -> {
                    Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                }
                is FinanzasViewModel.FinanzasState.Error -> {
                    Toast.makeText(context, "Error al Registrar", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}