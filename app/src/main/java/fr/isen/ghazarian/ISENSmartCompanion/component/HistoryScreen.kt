package fr.isen.ghazarian.isensmartcompanion.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
fun HistoryScreen(viewModel: InteractionViewModel) {
    val interactions by viewModel.interactions.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(interactions) { interaction ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = Color.LightGray, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(text = interaction.question, style = MaterialTheme.typography.bodyLarge)
                Text(text = interaction.response, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Date : ${interaction.timestamp}", style = MaterialTheme.typography.bodySmall)

                Row(horizontalArrangement = Arrangement.End) {
                    Button(onClick = { viewModel.deleteInteraction(interaction) }) {
                        Text("Supprimer")
                    }
                }
            }
        }

        item {
            Button(onClick = { viewModel.deleteAllInteractions() }) {
                Text("Supprimer tout")
            }
        }
    }
}





