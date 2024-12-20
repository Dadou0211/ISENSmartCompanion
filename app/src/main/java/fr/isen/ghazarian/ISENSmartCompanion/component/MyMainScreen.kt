package fr.isen.ghazarian.isensmartcompanion.component

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.Room

import fr.isen.ghazarian.isensmartcompanion.R
import fr.isen.ghazarian.isensmartcompanion.basededonnees.AppDatabase
import fr.isen.ghazarian.isensmartcompanion.component.iagemini.generateText
import fr.isen.ghazarian.isensmartcompanion.component.interaction.Interaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMainScreen() {
    val textListState = remember { mutableStateListOf<String>() }
    var userInput by remember { mutableStateOf("") } // Stocke la question posée par l'utilisateur
    val questionsAndResponses =
        remember { mutableStateListOf<Pair<String, String>>() } // Historique
    val context = LocalContext.current // Contexte nécessaire pour afficher un Toast
    val coroutineScope = rememberCoroutineScope() // Pour exécuter des tâches suspendues
    val database = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app_database"
    ).build()
    val interactionDao = database.interactionDao()

    // Fond de l'écran avec un gradient léger
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB)))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo centré en haut
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Image logo",
            modifier = Modifier.size(200.dp).padding(top = 16.dp),
        )

        // Historique des questions et réponses avec une LazyColumn
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1F)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(questionsAndResponses) { text ->
                // Envelopper les questions et réponses dans des Card pour un effet visuel agréable
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = text.first,
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF3F51B5), fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = text.second,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
                        )
                    }
                }
            }
        }

        // Zone de saisie pour la question et le bouton
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Entrez votre question") },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(68.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.textFieldColors(

                    focusedIndicatorColor = Color(0xFF3F51B5),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Button(
                onClick = {
                    if (userInput.isNotEmpty()) {
                        val newText = userInput.trim()
                        userInput = ""
                        coroutineScope.launch {
                            val geminiResponse = generateText(newText)

                            val interaction = Interaction(
                                question = "Question : $newText",
                                response = "Réponse : $geminiResponse"
                            )
                            interactionDao.insertInteraction(interaction)
                            questionsAndResponses.add(
                                Pair("Question : $newText", "Réponse : $geminiResponse")
                            )
                        }
                    }
                },
                modifier = Modifier
                    .height(68.dp)
                    .width(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)) // Couleur de fond correcte pour Button
            ) {
                Text("Ent", color = Color.White)
            }
        }
    }
}





