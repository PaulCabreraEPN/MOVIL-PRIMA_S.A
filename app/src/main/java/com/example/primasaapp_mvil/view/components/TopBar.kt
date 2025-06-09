package com.example.primasaapp_mvil.view.components

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primasaapp_mvil.R
import com.example.primasaapp_mvil.model.Seller
import com.example.primasaapp_mvil.ui.theme.Blue50
import com.example.primasaapp_mvil.view.login.LoginViewModel
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.primasaapp_mvil.model.SellerUpdate
import com.example.primasaapp_mvil.viewmodel.UserViewModel


@Composable
fun TopBar(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Blue50
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_atlas_w),
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate("profile")
                    }
            )
        }
    }
}





@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    onBack: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val defaultProfileImage = painterResource(id = R.drawable.perfil_icon)

    val username by userViewModel.username.collectAsState()
    val name by userViewModel.Name.collectAsState()
    val salesCity by userViewModel.sales_CITY.collectAsState()
    val cedula by userViewModel.cedula.collectAsState()
    val email by userViewModel.email.collectAsState()
    val phone by userViewModel.phone.collectAsState()

    var isEditingEmail by remember { mutableStateOf(false) }
    var isEditingPhone by remember { mutableStateOf(false) }

    var editableEmail by remember { mutableStateOf("") }
    var editablePhone by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val updateResult by userViewModel.updateResult.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    val isModified = (editableEmail != (email ?: "")) || (editablePhone != (phone ?: ""))

    val isFormValid = isModified &&
            emailError == null &&
            phoneError == null &&
            editableEmail.isNotBlank() &&
            editablePhone.isNotBlank()

    LaunchedEffect(email) {
        if (!isEditingEmail && email != null) {
            editableEmail = email
        }
    }

    LaunchedEffect(phone) {
        if (!isEditingPhone && phone != null) {
            editablePhone = phone
        }
    }

    // Mostrar snackbar con resultado update
    LaunchedEffect(updateResult) {
        updateResult?.onSuccess { message ->
            snackbarHostState.showSnackbar(
                message = message ?: "Perfil actualizado con éxito",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )
            navController.navigate("home")

        }?.onFailure { error ->
            snackbarHostState.showSnackbar(
                message = error.message ?: "Error al actualizar el perfil",
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )

        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6F6F6)),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isError = data.visuals.message.contains("error", ignoreCase = true)
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isError) Color.Red else Color(0xFF005BBB),
                    contentColor = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .padding(padding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mi perfil",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF005BBB)
                    )
                )

                Text(
                    text = "Cerrar Sesión",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.clickable { onLogout() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = defaultProfileImage,
                    contentDescription = "Imagen de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = username ?: "",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = name ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                ProfileField(label = "Cédula", value = cedula?.toString() ?: "")
                Spacer(modifier = Modifier.height(8.dp))

                ProfileField(label = "Ciudad de Ventas", value = salesCity ?: "")
                Spacer(modifier = Modifier.height(8.dp))

                EditableProfileField(
                    label = "Correo electrónico",
                    value = editableEmail,
                    isEditing = isEditingEmail,
                    onEditClick = { isEditingEmail = !isEditingEmail },
                    onValueChange = { editableEmail = it },
                    isError = emailError != null
                )
                emailError?.let {
                    Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                }

                Spacer(modifier = Modifier.height(8.dp))

                EditableProfileField(
                    label = "Teléfono",
                    value = editablePhone,
                    isEditing = isEditingPhone,
                    onEditClick = { isEditingPhone = !isEditingPhone },
                    onValueChange = { editablePhone = it },
                    isError = phoneError != null
                )
                phoneError?.let {
                    Text(it, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                }

            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val datosUpdate = SellerUpdate(
                        email = editableEmail,
                        PhoneNumber = editablePhone
                    )
                    userViewModel.updateProfile(datosUpdate)
                    isEditingEmail = false
                    isEditingPhone = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = isFormValid && !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFF1E519D) else Color.Gray,
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Guardar cambios", color = Color.White)
                }
            }
        }
    }
}




@Composable
fun ProfileField(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyLarge)
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Composable
fun EditableProfileField(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        enabled = true,
        trailingIcon = {
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (isEditing) "Guardar" else "Editar"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

