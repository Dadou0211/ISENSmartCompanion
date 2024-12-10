package fr.isen.ghazarian.isensmartcompanion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.ghazarian.isensmartcompanion.component.AgendaScreen
import fr.isen.ghazarian.isensmartcompanion.component.Event
import fr.isen.ghazarian.isensmartcompanion.component.MyMainScreen

class EventDetailActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Récupération des données passées via l'Intent
        val event = intent.getSerializableExtra("event") as Event

        setContent {
            val navController = rememberNavController() // Initialise NavController

            // Création de la navigation
            NavHost(navController = navController, startDestination = "event_detail") {
                composable("event_detail") {
                    EventDetailScreen(
                        title = event.title,
                        description = event.description,
                        date = event.date,
                        location = event.location,
                        category = event.category,
                        navController = navController // Passer navController ici
                    )
                }
                composable("agenda") {
                    AgendaScreen()
                }
                composable("my_main_screen") { // Ajouter cette route
                    MyMainScreen() // Naviguer vers MyMainScreen
                }
            }
        }
    }
}

@Composable
fun EventDetailScreen(
    title: String,
    description: String,
    date: String,
    location: String,
    category: String,
    navController: NavController // Ajoutez NavController ici pour la navigation
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background) // Arrière-plan général de l'écran
    ) {
        // Header Section avec la flèche de retour
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { navController.navigate("my_main_screen") } // Retourner à l'écran précédent (MyMainScreen)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Retour",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Titre de l'événement
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Event Detail Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                EventDetailRow(label = "Date", value = date)
                Spacer(modifier = Modifier.height(8.dp))
                EventDetailRow(label = "Lieu", value = location)
                Spacer(modifier = Modifier.height(8.dp))
                EventDetailRow(label = "Catégorie", value = category)
            }
        }

        // Description de l'événement
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF424242)),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )

        // Bouton pour naviguer vers AgendaScreen
        Button(
            onClick = { navController.navigate("agenda") }, // Naviguer vers l'écran Agenda
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "Ajouter à mon Agenda", style = MaterialTheme.typography.bodyLarge)
        }
    }
}


@Composable
fun EventDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF757575))
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
