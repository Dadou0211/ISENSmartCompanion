package fr.isen.ghazarian.isensmartcompanion.component

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.ghazarian.isensmartcompanion.EventDetailActivity
import fr.isen.ghazarian.isensmartcompanion.component.Api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.Serializable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


data class Event(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val category: String
) : Serializable


fun fetchEvent(events : MutableState<List<Event>>, context : Context){
    val call = RetrofitInstance.api.getEvents()


    call.enqueue(object : Callback<List<Event>> {
        override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
            if (response.isSuccessful) {
                events.value = response.body() ?: emptyList()
            }
        }

        override fun onFailure(call: Call<List<Event>>, t: Throwable) {

        }
    })
}


@Composable
fun EventScreen() {
    // Utiliser le ViewModel associé au cycle de vie via viewModel()
    val events = remember { mutableStateOf<List<Event>>(emptyList())}
    val context = LocalContext.current


    LaunchedEffect(Unit) {
            fetchEvent(events, context)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (events.value.isEmpty()) {
            Text("Chargement des événements...", style = MaterialTheme.typography.bodyLarge)
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp)
            ) {
                items(events.value) { event ->
                    EventItem(event = event) { selectedEvent ->

                        val intent = Intent(context, EventDetailActivity::class.java).apply {
                            putExtra("event", selectedEvent)

                        }
                        context.startActivity(intent)
                    }
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
            .fillMaxWidth()
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

