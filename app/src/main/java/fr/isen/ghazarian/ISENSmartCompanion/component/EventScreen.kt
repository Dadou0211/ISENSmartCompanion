package fr.isen.ghazarian.isensmartcompanion.component

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import fr.isen.ghazarian.isensmartcompanion.EventDetailActivity


data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
)


@Composable
fun EventScreen() {
    val context = LocalContext.current

    // Liste fictive d'événements
    val eventList = listOf(
        Event(1, "Soirée BDE", "Une soirée mémorable pour tous les étudiants.", "2024-11-25", "Salle des fêtes", "Fête"),
        Event(2, "Gala ISEN", "Le gala annuel de l'école avec dîner et spectacles.", "2024-12-10", "Hôtel de ville", "Gala"),
        Event(3, "Journée de cohésion", "Activités de groupe pour renforcer les liens.", "2024-11-30", "Campus ISEN", "Atelier")
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(eventList) { event ->
                EventItem(event = event) { selectedEvent ->
                    // Intent pour démarrer EventDetailActivity
                    val intent = Intent(context, EventDetailActivity::class.java).apply {
                        putExtra("eventId", selectedEvent.id)
                        putExtra("eventTitle", selectedEvent.title)
                        putExtra("eventDescription", selectedEvent.description)
                        putExtra("eventDate", selectedEvent.date)
                        putExtra("eventLocation", selectedEvent.location)
                        putExtra("eventCategory", selectedEvent.category)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun EventItem(event: Event, onClick: (Event) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { onClick(event) }
    ) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
