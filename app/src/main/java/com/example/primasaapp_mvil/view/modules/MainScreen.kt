package com.example.primasaapp_mvil.view.modules

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.primasaapp_mvil.ui.theme.PRIMASAAPPMóvilTheme
import com.example.primasaapp_mvil.view.components.BottomNavBar
import com.example.primasaapp_mvil.view.components.NavigationHost
import com.example.primasaapp_mvil.view.components.TopBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PRIMASAAPPMóvilTheme {
                MainScreen()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: "home"
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavBar(navController, currentRoute) },
        containerColor = Color(0xFFF6F6F6)
    ) { innerPadding ->
        NavigationHost(navController, Modifier.padding(innerPadding))
    }
}

