package com.domusos.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.domusos.app.presentation.ShoppingScreen
import com.domusos.app.presentation.ShoppingUiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate()
        setContent {
            MaterialTheme {
                Surface {
                    ShoppingScreen(state = ShoppingUiState.Idle)
                }
            }
        }
    }
}