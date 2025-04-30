package com.example.primasaapp_mvil.view.modules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.primasaapp_mvil.model.Client
import com.example.primasaapp_mvil.viewmodel.ClientViewModel

@Composable
fun HomeScreen() {
    Text(text = "Pantalla de Inicio")
}

@Composable
fun OrdersScreen() {
    Text(text = "Pantalla de Pedidos")
}

@Composable
fun ClientsScreen(viewModel: ClientViewModel = hiltViewModel()) {
    val client by viewModel.clients.collectAsState()

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        items(client) {
            client -> ClientCard(client)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun  ClientCard(client: Client){
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxSize()
    ){
        Column(modifier = Modifier
            .padding(16.dp)){
            Text(text = client.Name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "RUC ${client.Ruc}")
            Text(text = "Teléfono ${client.telephone}")
            Text(text = "Email ${client.email}")
            Text(text = "Crédito ${client.credit}")
            Text(text = "Dirección ${client.Adress}")
            Text(
                text = client.state,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DocumentsScreen() {
    Text(text = "Pantalla de Documentos")
}
