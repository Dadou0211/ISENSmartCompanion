package fr.isen.ghazarian.isensmartcompanion.agenda

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class AgendaViewModel( private val eventDao: EventDao) : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val selectedDate: StateFlow<LocalDate> = _selectedDate

    @RequiresApi(Build.VERSION_CODES.O)
    private val _currentMonth = MutableStateFlow(LocalDate.now())
    @RequiresApi(Build.VERSION_CODES.O)
    val currentMonth: StateFlow<LocalDate> = _currentMonth

    @RequiresApi(Build.VERSION_CODES.O)
    val eventsForSelectedDate: Flow<List<Event>> =
        _selectedDate.flatMapLatest { date -> eventDao.getEventsForDate(date) }

    val eventDates: Flow<List<LocalDate>> = eventDao.getAllEventDates()

    @RequiresApi(Build.VERSION_CODES.O)
    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeMonth(delta: Int) {
        _currentMonth.value = _currentMonth.value.plusMonths(delta.toLong())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEvent(description: String) {
        viewModelScope.launch {
            eventDao.insertEvent(Event(date = _selectedDate.value, description = description))
        }
    }

    fun deleteEvent(eventId: Int) {
        viewModelScope.launch {
            eventDao.deleteEvent(eventId)
        }
    }
}
