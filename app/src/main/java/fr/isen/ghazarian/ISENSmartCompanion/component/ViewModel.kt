package fr.isen.ghazarian.isensmartcompanion.component

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.isen.ghazarian.isensmartcompanion.component.dao.InteractionDao
import fr.isen.ghazarian.isensmartcompanion.component.interaction.Interaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class InteractionViewModel(interactionDao: InteractionDao) : ViewModel() {
    val interactions: Flow<List<Interaction>> = interactionDao.getAllInteractions()
    private val _interactionDao = interactionDao

    fun deleteInteraction(interaction: Interaction) {
        viewModelScope.launch {
            _interactionDao.deleteInteraction(interaction)
        }
    }

    fun deleteAllInteractions() {
        viewModelScope.launch {
            _interactionDao.deleteAllInteractions()
        }
    }
}
