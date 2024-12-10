package fr.isen.ghazarian.isensmartcompanion

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import fr.isen.ghazarian.isensmartcompanion.agenda.AgendaViewModel
import fr.isen.ghazarian.isensmartcompanion.basededonnees.AppDatabase
import fr.isen.ghazarian.isensmartcompanion.component.AgendaScreen
import fr.isen.ghazarian.isensmartcompanion.component.EventScreen
import fr.isen.ghazarian.isensmartcompanion.component.HistoryScreen
import fr.isen.ghazarian.isensmartcompanion.component.InteractionViewModel
import fr.isen.ghazarian.isensmartcompanion.component.MyMainScreen


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppWithBottomNavigation()
        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppWithBottomNavigation() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val database = Room.databaseBuilder(
        LocalContext.current,
        AppDatabase::class.java,
        "app_database"
    ).build()
    val interactionDao = database.interactionDao()
    val viewModel = remember { InteractionViewModel(interactionDao) }



    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main_screen") { MyMainScreen() }
            composable("events_screen") { EventScreen() }
            composable("agenda_screen") { AgendaScreen() }
            composable("history_screen") { HistoryScreen(viewModel) }

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem("Accueil", "main_screen", Icons.Default.Home),
        NavigationItem("Événements", "events_screen", Icons.Default.CheckCircle),
        NavigationItem("Agenda", "agenda_screen", Icons.Default.DateRange),
        NavigationItem("Historique", "history_screen", Icons.Default.Notifications)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = false, // Vous pouvez gérer l'état sélectionné ici.
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }


}

data class NavigationItem(
    val label: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewAppWithBottomNavigation() {
    AppWithBottomNavigation()
}
