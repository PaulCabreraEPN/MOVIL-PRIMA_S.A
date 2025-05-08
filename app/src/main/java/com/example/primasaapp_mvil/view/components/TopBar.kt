package com.example.primasaapp_mvil.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primasaapp_mvil.R
import com.example.primasaapp_mvil.ui.theme.Blue50

@Composable
fun TopBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Blue50 // Azul oscuro (puedes ajustar si quieres)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo a la izquierda
            Image(
                painter = painterResource(id = R.drawable.logo_atlas_w), // Tu logo aquí
                contentDescription = "Logo",
                modifier = Modifier.size(48.dp)
            )

            // Icono de perfil a la derecha
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Icono de perfil
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                    }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}

