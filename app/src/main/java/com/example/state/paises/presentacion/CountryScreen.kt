package com.example.state.paises.presentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.state.paises.data.model.Country
import com.example.state.ui.theme.Beige
import com.example.state.ui.theme.GrayBlue
import com.example.state.ui.theme.LightGray
import com.example.state.ui.theme.Teal

@Preview(showBackground = false)
@Composable
fun CountryScreen(viewModel: CountryViewModel = viewModel()) {
    val countries by viewModel.countries.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(initial = false)
    val errorMessage by viewModel.errorMessage.collectAsState()
    val showModal by viewModel.showModal.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCountries()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Teal)
            .padding(16.dp)
    ) {

            Column {
                Text(
                    text = "Países",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Beige,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                if (!errorMessage.isNullOrEmpty()) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Button(
                    onClick = { viewModel.toggleModal() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Beige, contentColor = Teal)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar País")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar País")
                }

                countries.forEach { country ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = LightGray)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nombre: ${country.nombre}", fontWeight = FontWeight.Bold, color = Teal)
                            Text(text = "Código ISO: ${country.codigo_iso}", color = GrayBlue)
                            Text(text = "Capital: ${country.capital}", color = GrayBlue)
                            Text(text = "Población: ${country.poblacion}", color = GrayBlue)
                            Text(text = "Idioma: ${country.idioma_principal}", color = GrayBlue)
                        }
                    }
                }
            }
        }

    if (showModal) {
        AddCountryModal(viewModel = viewModel)
    }
}

@Composable
fun AddCountryModal(viewModel: CountryViewModel) {
    var nombre by remember { mutableStateOf("") }
    var codigoIso by remember { mutableStateOf("") }
    var capital by remember { mutableStateOf("") }
    var poblacion by remember { mutableStateOf("") }
    var idioma_principal by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { viewModel.toggleModal() }) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = LightGray)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Agregar País",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Teal,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                )
                TextField(
                    value = codigoIso,
                    onValueChange = { codigoIso = it },
                    label = { Text("Código ISO") },
                )
                TextField(
                    value = capital,
                    onValueChange = { capital = it },
                    label = { Text("Capital") },
                )
                TextField(
                    value = poblacion,
                    onValueChange = { poblacion = it },
                    label = { Text("Población") },
                    )
                TextField(
                    value = idioma_principal,
                    onValueChange = { idioma_principal = it },
                    label = { Text("Idioma") },
                    )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { viewModel.toggleModal() },
                        colors = ButtonDefaults.buttonColors(containerColor = Teal, contentColor = LightGray)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cancelar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cancelar")
                    }
                    Button(
                        onClick = {
                            viewModel.addCountry(
                                Country(
                                    nombre = nombre,
                                    codigo_iso = codigoIso,
                                    capital = capital,
                                    poblacion = poblacion.toIntOrNull() ?: 0,
                                    idioma_principal = idioma_principal
                                )
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Beige, contentColor = Teal)
                    ) {
                        Icon(Icons.Default.Done, contentDescription = "Guardar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar")
                    }
                }
            }
        }
    }
}
