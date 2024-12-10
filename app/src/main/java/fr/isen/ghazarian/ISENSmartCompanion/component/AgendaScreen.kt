package fr.isen.ghazarian.isensmartcompanion.component

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AgendaScreen() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var events by remember { mutableStateOf(mutableMapOf<LocalDate, MutableList<String>>()) }
    var showDialog by remember { mutableStateOf(false) }
    var newEventText by remember { mutableStateOf("") }

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }  // Utiliser YearMonth pour gérer mois et année

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).background(Brush.verticalGradient(listOf(Color(0xFFE3F2FD), Color(0xFFBBDEFB)))) ) {
        // Header: Navigation des mois et année
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Bouton précédent
            IconButton(onClick = {
                currentMonth = currentMonth.minusMonths(1)  // Changer pour le mois précédent
            }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Mois d'avant", tint = Color(0xFF3F51B5))
            }

            // Affichage du mois et de l'année
            Text(
                text = currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentMonth.year,
                style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF3F51B5)),
                modifier = Modifier.align(Alignment.CenterVertically)
            )

            // Bouton suivant
            IconButton(onClick = {
                currentMonth = currentMonth.plusMonths(1)  // Changer pour le mois suivant
            }) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Mois d'après", tint = Color(0xFF3F51B5))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Weekdays Row
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            val daysOfWeek = listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim")
            for (day in daysOfWeek) {
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF3F51B5)),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Month Grid
        MonthGrid(
            yearMonth = currentMonth,
            selectedDate = selectedDate,
            onDateSelected = { date -> selectedDate = date },
            events = events // Pass the events map here
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Date Display
        Text(
            text = "Date Sélectionnée: $selectedDate",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Events for Selected Date
        val selectedEvents = events[selectedDate] ?: mutableListOf()
        if (selectedEvents.isNotEmpty()) {
            Text(
                text = "Evènements :",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Column {
                for ((index, event) in selectedEvents.withIndex()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = event,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            // Remove the event
                            events[selectedDate]?.removeAt(index)
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Effacer L'évènement", tint = Color.Red)
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Aucun Evènement",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to Add Event
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .background(Color(0xFF3F51B5), shape = RoundedCornerShape(12.dp))
        ) {
            Text(text = "Ajouter un Evènement", color = Color.White)
        }

        // Dialog for Adding Event
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Ajouter un Evènement", color = Color(0xFF3F51B5)) },
                text = {
                    Column {
                        Text(text = "Entrez les détails de l'évènement:", color = Color(0xFF3F51B5))
                        Spacer(modifier = Modifier.height(8.dp))
                        BasicTextField(
                            value = newEventText,
                            onValueChange = { newEventText = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newEventText.isNotBlank()) {
                                events.getOrPut(selectedDate) { mutableListOf() }.add(newEventText)
                                newEventText = ""
                            }
                            showDialog = false
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ajouter")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }, shape = RoundedCornerShape(12.dp)) {
                        Text("Annuler")
                    }
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthGrid(
    yearMonth: YearMonth,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    events: Map<LocalDate, MutableList<String>> // New parameter to pass events
) {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()
    val daysInMonth = (1..lastDayOfMonth.dayOfMonth).toList()
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust to start from Monday

    val gridCells = List(firstDayOfWeek) { null } + daysInMonth.map { day ->
        yearMonth.atDay(day)
    }

    val rows = gridCells.chunked(7) // Group days into weeks

    Column {
        for (week in rows) {
            Row(modifier = Modifier.fillMaxWidth()) {
                for (date in week) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clickable(enabled = date != null) {
                                date?.let { onDateSelected(it) }
                            }
                            .background(
                                if (date == selectedDate) Color(0xFFE3F2FD) else Color.Transparent
                            )
                            .then(
                                if (date != null && events.containsKey(date) && events[date]?.isNotEmpty() == true) {
                                    Modifier.background(Color(0xFFE6B2A3)) // Highlight days with events
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date?.dayOfMonth?.toString() ?: "",
                            fontSize = 16.sp,
                            color = if (date != null && events.containsKey(date) && events[date]?.isNotEmpty() == true) {
                                Color.Red // Change text color for days with events
                            } else {
                                Color.Black
                            }
                        )
                    }
                }
            }
        }
    }
}
