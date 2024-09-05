import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

// Key definition
val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

// Read data from DataStore
fun exampleCounterFlow(context: Context): Flow<Int> =
    context.dataStore.data
        .map { preferences ->
            preferences[EXAMPLE_COUNTER] ?: 0
        }

// Save data to DataStore
suspend fun incrementCounter(context: Context) {
    context.dataStore.edit { preferences ->
        val currentCounterValue = preferences[EXAMPLE_COUNTER] ?: 0
        preferences[EXAMPLE_COUNTER] = currentCounterValue + 1
    }
}
