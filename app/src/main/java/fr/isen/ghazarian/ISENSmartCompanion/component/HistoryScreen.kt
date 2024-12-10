package fr.isen.ghazarian.isensmartcompanion.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.ghazarian.isensmartcompanion.R

@Composable
fun HistoryScreen(viewModel: InteractionViewModel) {
    val interactions by viewModel.interactions.collectAsState(initial = emptyList())

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(interactions) { interaction ->
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .shadow(5.dp, RoundedCornerShape(16.dp)) // Adding subtle shadow for depth
                    .padding(16.dp)
            ) {
                Text(
                    text = interaction.question,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF3F51B5))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = interaction.response,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF424242))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Date : ${interaction.timestamp}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF757575))
                )

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { viewModel.deleteInteraction(interaction) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Delete Interaction",
                            tint = Color(0xFFE57373)
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.deleteAllInteractions() },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Supprimer tout", color = Color.White)
            }
        }
    }
}
