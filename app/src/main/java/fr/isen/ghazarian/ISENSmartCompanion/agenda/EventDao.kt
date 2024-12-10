package fr.isen.ghazarian.isensmartcompanion.agenda

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface EventDao {
    @Query("SELECT * FROM events WHERE date = :date")
    fun getEventsForDate(date: LocalDate): Flow<List<Event>>

    @Query("SELECT date FROM events")
    fun getAllEventDates(): Flow<List<LocalDate>>

    @Insert
    suspend fun insertEvent(event: Event)

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun deleteEvent(eventId: Int)
}
