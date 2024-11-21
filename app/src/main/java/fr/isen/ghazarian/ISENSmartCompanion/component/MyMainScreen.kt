package fr.isen.ghazarian.isensmartcompanion.component

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.ghazarian.isensmartcompanion.R

@Composable
fun MyMainScreen() {
    val textListState = remember { mutableStateListOf<String>() }
    val currentTextState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Image logo",
                modifier = Modifier.size(200.dp),
            )

            LazyColumn(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(textListState) { text ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                            modifier = Modifier
                                .padding(8.dp)
                                .background(color = Color.LightGray,shape = RoundedCornerShape(16.dp))
                                .padding(8.dp, 3.dp)

                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .height(268.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = currentTextState.value,
                onValueChange = { currentTextState.value = it },
                label = { Text("Entrez votre texte ici") },
                modifier = Modifier
                    .width(300.dp)
                    .height(68.dp)

            )


            Button(onClick = {
                val newText = currentTextState.value.trim()
                if (newText.isNotEmpty()) {
                    textListState.add(newText)
                    currentTextState.value = ""
                    println("Texte enregistré : $newText")
                }

            }) {
                Text("Valider")
            }

        }
    }
}