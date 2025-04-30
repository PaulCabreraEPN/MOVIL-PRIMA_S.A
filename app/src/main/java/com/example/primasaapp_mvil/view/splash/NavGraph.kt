package com.example.primasaapp_mvil.view.splash

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.primasaapp_mvil.R
import com.example.primasaapp_mvil.view.login.LoginActivity

@Composable
fun SplashNavGraph(navController: NavHostController = rememberNavController()) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "splash1"
    ) {
        composable("splash1") {
            SplashScreenTemplate(
                imageRes = R.drawable.sales_person_1,
                title = "¡Bienvenido a PRIMA S.A.!",
                description = "Facilita tu trabajo y gestiona tus ventas de forma rápida y sencilla.",
                bottomText = "¡Empecemos!",
                currentIndex = 0,
                onNextClick = { navController.navigate("splash2") },
                onSkipClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                onPreviousClick = { }
            )
        }

        composable("splash2") {
            SplashScreenTemplate(
                imageRes = R.drawable.sales_person_2,
                title = "Gestiona tus ventas sin esfuerzo",
                description = "Consulta tu inventario, clientes y pedidos en un solo lugar. ¡Ahorra tiempo y aumenta tu productividad!",
                bottomText = "",
                currentIndex = 1,
                onNextClick = { navController.navigate("splash3") },
                onSkipClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                onPreviousClick = { navController.navigate("splash1") }
            )
        }

        composable("splash3") {
            SplashScreenTemplate(
                imageRes = R.drawable.sales_person_3,
                title = "Tu información siempre disponible",
                description = "Inicia sesión, personaliza tu perfil y accede a tus datos con total seguridad.",
                bottomText = "Iniciar sesión",
                currentIndex = 2,
                onNextClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                onSkipClick = {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                },
                onPreviousClick = { navController.navigate("splash2") }
            )
        }
    }
}


