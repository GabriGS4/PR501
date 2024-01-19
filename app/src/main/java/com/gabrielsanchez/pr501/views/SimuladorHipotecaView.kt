package com.gabrielsanchez.pr501.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabrielsanchez.pr501.viewmodels.SimuladorHipotecaViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimuladorHipotecaView(viewModel: SimuladorHipotecaViewModel) {
    val capital: String by viewModel.capital.observeAsState("")
    val plazo: String by viewModel.plazo.observeAsState("")
    var context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Calcular Cuota Hipoteca",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) {
        ContentCalcular(it, viewModel, capital, plazo, context = context)
    }
}

@Composable
fun ContentCalcular(
    it: PaddingValues,
    viewModel: SimuladorHipotecaViewModel,
    capital: String,
    plazo: String,
    context: Context
) {
    val cuota = remember { mutableStateOf(0.0) }
    val showLoading = remember { mutableStateOf(false) }
    val showCuota = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = capital,
            onValueChange = {
                viewModel.setCapital(it)
            },
            label = { Text(text = "Capital") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        OutlinedTextField(
            value = plazo,
            onValueChange = {
                viewModel.setPlazo(it)
            },
            label = { Text(text = "Plazo") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp)
        )

        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            onClick = {
                if (!viewModel.checkCapitalAndPlazo()) {
                    Toast.makeText(
                        context,
                        "El Capital y el Plazo son requeridos",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@ElevatedButton
                }
                cuota.value = viewModel.calcularHipoteca()
                showCuota.value = false
                showLoading.value = true
            }
        ) {
            Text(text = "Calcular Cuota Mensual")
        }

        if (showCuota.value) {
            CuotaView(cuota)
        }

        if (showLoading.value) {
            LoadingView(showLoading, showCuota)
        }
    }
}

@Composable
fun LoadingView(showLoading: MutableState<Boolean>, showCuota: MutableState<Boolean>) {
    LaunchedEffect(Unit) {
        delay(2500) // wait 2.5 seconds
        showLoading.value = false
        showCuota.value = true
    }
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun CuotaView(cuota: MutableState<Double>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${"%.2f".format(cuota.value)}",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            color = Color.Red
        )
    }
}