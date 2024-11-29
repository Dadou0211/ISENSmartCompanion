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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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


@Composable
fun MyMainScreen() {
    val textListState = remember { mutableStateListOf<String>() }
    /*val currentTextState = remember { mutableStateOf("") }*/
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



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Image logo",
            modifier = Modifier.size(200.dp),
        )

        LazyColumn(
            modifier = Modifier
                .padding(8.dp)
                .weight(1F)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(questionsAndResponses) { text ->
                Text(
                    text = text.first,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp, 3.dp)
                )
                Text(
                    text = text.second,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp, 3.dp)
                )

            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Entrez votre texte ici") },
                modifier = Modifier
                    .width(300.dp)
                    .height(68.dp)

            )


            Button(onClick = {
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
                            Pair("Question : $newText", "Réponse : $geminiResponse"))
                    }
                }
            }) {
                Text("Valider")
            }


        }
    }
}


