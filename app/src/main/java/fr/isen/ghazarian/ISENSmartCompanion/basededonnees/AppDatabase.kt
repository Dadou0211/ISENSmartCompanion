package fr.isen.ghazarian.isensmartcompanion.basededonnees

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.isen.ghazarian.isensmartcompanion.component.DateTypeConverter
import fr.isen.ghazarian.isensmartcompanion.component.dao.InteractionDao
import fr.isen.ghazarian.isensmartcompanion.component.interaction.Interaction

import java.util.Date

@Database(entities = [Interaction::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun interactionDao(): InteractionDao
}
