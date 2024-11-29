package fr.isen.ghazarian.isensmartcompanion.component.dao

import androidx.room.*
import fr.isen.ghazarian.isensmartcompanion.component.interaction.Interaction
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractionDao {
    @Query("SELECT * FROM interactions ORDER BY timestamp DESC")
    fun getAllInteractions(): Flow<List<Interaction>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInteraction(interaction: Interaction)

    @Delete
    suspend fun deleteInteraction(interaction: Interaction)

    @Query("DELETE FROM interactions")
    suspend fun deleteAllInteractions()
}
