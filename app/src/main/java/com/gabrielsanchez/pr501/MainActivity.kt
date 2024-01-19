package com.gabrielsanchez.pr501

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabrielsanchez.pr501.ui.theme.PR501Theme
import com.gabrielsanchez.pr501.viewmodels.SimuladorHipotecaViewModel
import com.gabrielsanchez.pr501.views.SimuladorHipotecaView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR501Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = SimuladorHipotecaViewModel();
                    SimuladorHipotecaView(viewModel = viewModel)
                }
            }
        }
    }
}