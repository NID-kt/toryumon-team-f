import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

// Key definition
val EXAMPLE_COUNTER = intPreferencesKey("example_counter")
val bearName = stringPreferencesKey("bearName")
val targetPeriod = stringPreferencesKey("target_period")
val targetSteps = stringPreferencesKey("target_steps")
val userName = stringPreferencesKey("UserName")
val userIcon = stringPreferencesKey("UserIcon")
val enthusiaasm = stringPreferencesKey("Enthusiasm")
