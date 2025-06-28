package com.example.primasaapp_mvil

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.net.URL
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.primasaapp_mvil.ui.theme.PRIMASAAPPMóvilTheme
import java.net.HttpURLConnection

class MainSplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PRIMASAAPPMóvilTheme {
                SplashScreen() // Tu composable tal como lo definiste
            }
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    var isServerReady by remember { mutableStateOf(false) }
    var rawProgress by remember { mutableStateOf(0f) }

    // Fondo animado
    val infiniteTransition = rememberInfiniteTransition()
    val color1 by infiniteTransition.animateColor(
        initialValue = Color(0xFF0D47A1),
        targetValue = Color(0xFF1565C0),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFF1565C0),
        targetValue = Color(0xFF0D47A1),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Difuminado negro que sube (animado)
    val overlayProgress by animateFloatAsState(
        targetValue = rawProgress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 500),
        label = "overlayProgress"
    )

    // Verificar servidor y aumentar el difuminado negro
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            repeat(20) {
                try {
                    val connection = URL("https://back-prima-s-a.onrender.com")
                        .openConnection() as HttpURLConnection
                    connection.connectTimeout = 3000
                    connection.readTimeout = 3000
                    connection.inputStream.close()
                    isServerReady = true
                    return@withContext
                } catch (e: Exception) {
                    delay(1000)
                    withContext(Dispatchers.Main) {
                        rawProgress += 0.05f // más visible
                    }
                }
            }
        }
    }

    // Navegar cuando el servidor esté listo
    LaunchedEffect(isServerReady) {
        if (isServerReady) {
            delay(500)
            context.startActivity(Intent(context, MainActivity::class.java))
            (context as? Activity)?.finish()
        }
    }

    // Fondo + difuminado + contenido
    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo animado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(color1, color2)))
        )

        // Difuminado negro que sube
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(overlayProgress)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )

        // Logo
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_atlas_w),
                contentDescription = "Logo",
                modifier = Modifier.size(130.dp)
            )
        }

        // Texto ATLAS como imagen
        Image(
            painter = painterResource(id = R.drawable.title_atlas_w),
            contentDescription = "Texto ATLAS",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .height(60.dp)
        )
    }
}



