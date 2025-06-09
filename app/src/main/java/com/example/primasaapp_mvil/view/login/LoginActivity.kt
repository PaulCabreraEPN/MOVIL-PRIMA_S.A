package com.example.primasaapp_mvil.view.login

import android.content.Intent
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.primasaapp_mvil.ui.theme.PRIMASAAPPMóvilTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.primasaapp_mvil.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
// Navegación principal
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Para pasar el navController a los composables
import androidx.navigation.NavController
import com.example.primasaapp_mvil.view.modules.MainScreenActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PRIMASAAPPMóvilTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("recovery") {
                            RecoveryFlow(navController)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RecoveryFlow(parentNavController: NavController) {
    val navController = rememberNavController()
    val PasswordViewlModel: PasswordViewlModel = hiltViewModel()

    NavHost(navController, startDestination = "step_email") {
        composable("step_email") {
            StepEmailScreen(
                viewModel = PasswordViewlModel,
                onNext = { navController.navigate("step_token") },
                onBack = { parentNavController.popBackStack() } // volver a login
            )
        }

        composable("step_token") {
            StepTokenScreen(
                viewModel = PasswordViewlModel,
                onNext = { navController.navigate("step_verify") },
                onBack = { navController.popBackStack() } // vuelve a email
            )
        }

        composable("step_verify") {
            ChangePassScreen(
                viewModel = PasswordViewlModel,
                onNext = { navController.navigate("step_success") },
                onBack = { navController.popBackStack() } // vuelve a token
            )
        }

        composable("step_success") {
            SuccesedScreen(
                onNext = { parentNavController.popBackStack() } // volver a login o pantalla anterior
            )
        }
    }
}





@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val state = viewModel.loginState

    // Detectar login exitoso y lanzar nueva Activity
    LaunchedEffect(state?.isSuccess) {
        if (state?.isSuccess == true) {
            context.startActivity(Intent(context, MainScreenActivity::class.java))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .background(Color(0xFF1E519D)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_atlas_w),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(185.dp)
                        .height(111.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.title_atlas_w),
                    contentDescription = "Texto Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(63.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Usuario
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de Usuario") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de iniciar sesión
        Button(
            onClick = {viewModel.login(username,password)},
            modifier = Modifier
                .width(206.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E519D),
                contentColor = Color.White
            )
        ) {
            Text("Iniciar Sesión")
        }

        when {
            state?.isSuccess == true -> {
                Text("Login exitoso", color = Color.Green)
                // Almacenar el token
            }
            state?.isFailure == true -> {
                Text("Error: ${state.exceptionOrNull()?.message}", color = Color.Red)
            }
        }


        Spacer(modifier = Modifier.weight(1f))


        // Botón de recuperación de contraseña
        Button(
            onClick = { navController.navigate("recovery") },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF6F6F6),
                contentColor = Color(0xFF999999)
            )
        ) {
            Text("¿Olvidaste tu contraseña?")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PRIMASAAPPMóvilTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginScreen(
                navController = rememberNavController() // NavController mock
            )
        }
    }
}


@Composable
fun StepEmailScreen(viewModel: PasswordViewlModel, onNext: () -> Unit, onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .background(Color(0xFF1E519D)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_atlas_w),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(185.dp)
                        .height(111.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.title_atlas_w),
                    contentDescription = "Texto Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(63.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Recuperar Contraseña",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Introduce el correo electrónico o el nombre de usuario asociados a tu cuenta para cambiar tu contraseña.",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Email
        TextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {
                viewModel.requestPasswordReset(
                    onSuccess = { msg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(msg)
                            onNext()
                        }
                    },
                    onError = { errorMsg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(errorMsg)
                        }
                    }
                )
            },
            modifier = Modifier
                .width(206.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E519D),
                contentColor = Color.White
            )
        ) {
            Text("Siguiente")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onBack() },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF6F6F6),
                contentColor = Color(0xFF999999)
            )
        ) {
            Text("Volver al Inicio de Sesión")
        }
    }

    // SnackbarHost fuera del layout principal
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun StepTokenScreen(
    viewModel: PasswordViewlModel = hiltViewModel(),
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .background(Color(0xFF1E519D)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_atlas_w),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(185.dp)
                        .height(111.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.title_atlas_w),
                    contentDescription = "Texto Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(63.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Verifica tu identidad",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ingresa el código que enviamos a tu correo electrónico para continuar con el cambio de contraseña.",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de token
        TextField(
            value = viewModel.token,
            onValueChange = { viewModel.token = it },
            label = { Text("Código de verificación") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.verifyToken(
                    viewModel.token,
                    onSuccess = { msg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(msg)
                            onNext()
                        }
                    },
                    onError = { errorMsg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(errorMsg)
                        }
                    }
                )
            },
            modifier = Modifier
                .width(206.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E519D),
                contentColor = Color.White
            )
        ) {
            Text("Verificar")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onBack() },
            modifier = Modifier.padding(bottom = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF6F6F6),
                contentColor = Color(0xFF999999)
            )
        ) {
            Text("Volver")
        }
    }

    // SnackbarHost fuera del layout principal
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        SnackbarHost(hostState = snackbarHostState)
    }
}


@Composable
fun ChangePassScreen(
    viewModel: PasswordViewlModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var passwordErrorMsg by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Validaciones
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$")
    val isPasswordStrong = passwordRegex.matches(password)
    val doPasswordsMatch = password == confirmPassword
    val areFieldsNotEmpty = password.isNotBlank() && confirmPassword.isNotBlank()
    val isFormValid = isPasswordStrong && doPasswordsMatch && areFieldsNotEmpty

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .background(Color(0xFF1E519D)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_atlas_w),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(185.dp)
                        .height(111.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.title_atlas_w),
                    contentDescription = "Texto Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(63.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Cambiar Contraseña",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Ingrese su nueva contraseña",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = {
                password = it
                passwordErrorMsg = if (!passwordRegex.matches(it)) {
                    "Debe tener al menos 8 caracteres, mayúscula, minúscula, número y símbolo"
                } else ""
            },
            label = { Text("Contraseña") },
            isError = passwordErrorMsg.isNotEmpty(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        if (passwordErrorMsg.isNotEmpty()) {
            Text(
                text = passwordErrorMsg,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 32.dp, top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Confirme su nueva contraseña",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = password != it
            },
            label = { Text("Confirmar contraseña") },
            isError = confirmPasswordError,
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(imageVector = icon, contentDescription = "Toggle Confirm Password Visibility")
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        if (confirmPasswordError) {
            Text(
                text = "Las contraseñas no coinciden",
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 32.dp, top = 4.dp)
                    .fillMaxWidth(0.8f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.newPassword = password
                viewModel.confirmPassword = confirmPassword
                viewModel.resetPasswordWithToken(
                    onSuccess = onNext,
                    onError = { errorMsg ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(errorMsg)
                        }
                    }
                )
            },
            enabled = isFormValid,
            modifier = Modifier
                .width(206.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFormValid) Color(0xFF1E519D) else Color.Gray,
                contentColor = Color.White
            )
        ) {
            Text("Siguiente")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Composable
fun SuccesedScreen(onNext: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Encabezado azul con logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f)
                .background(Color(0xFF1E519D)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_atlas_w),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(185.dp)
                        .height(111.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.title_atlas_w),
                    contentDescription = "Texto Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(63.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Contraseña modificada con éxito",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f),
            contentAlignment = Alignment.Center
        ){
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource( id = R.drawable.success_logo),
                    contentDescription = "Éxito Logo",
                    modifier = Modifier
                        .width(131.dp)
                        .height(131.dp)
                )
            }
        }



        Text(
            text = "Ya puedes iniciar sesión con tu nueva contraseña.",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de iniciar sesión
        Button(
            onClick = {
                onNext()
            },
            modifier = Modifier
                .width(206.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1E519D),
                contentColor = Color.White
            )
        ) {
            Text("Iniciar Sesión")
        }

    }
}






