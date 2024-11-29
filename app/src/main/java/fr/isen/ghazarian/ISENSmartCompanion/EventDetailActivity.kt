package fr.isen.ghazarian.isensmartcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import fr.isen.ghazarian.isensmartcompanion.component.Event

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération des données passées via l'Intent
        val event = intent.getSerializableExtra("event") as Event

        setContent {
            EventDetailScreen(
                title = event.title,
                description = event.description,
                date = event.date,
                location = event.location,
                category = event.category
            )
        }
    }
}

@Composable
fun EventDetailScreen(
    title: String,
    description: String,
    date: String,
    location: String,
    category: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = """
                Détails de l'événement :
                
                Titre : $title
                Description : $description
                Date : $date
                Lieu : $location
                Catégorie : $category
            """.trimIndent(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

