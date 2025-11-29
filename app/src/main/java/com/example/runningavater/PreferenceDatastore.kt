import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "dataStore")

// Key definition
val bearName = stringPreferencesKey("bearName")
val targetPeriod = stringPreferencesKey("target_period")
val targetSteps = intPreferencesKey("target_steps")
val userName = stringPreferencesKey("UserName")
val userIcon = stringPreferencesKey("UserIcon")
val enthusiaasm = stringPreferencesKey("Enthusiasm")
val currentLevelKey = intPreferencesKey("CurrentLevel")
val beforeLevelKey = intPreferencesKey("BeforeLevel")
val afterLevelKey = intPreferencesKey("AfterLevel")
val isInit = booleanPreferencesKey("IsInit")

val lastNotify20GoalSentDateKey = longPreferencesKey("LastNotify20GoalSentDate")

val lastNotify40GoalSentDateKey = longPreferencesKey("LastNotify40GoalSentDate")

val lastNotify50GoalSentDateKey = longPreferencesKey("LastNotify50GoalSentDate")

val lastNotify60GoalSentDateKey = longPreferencesKey("LastNotify60GoalSentDate")

val lastNotify80GoalSentDateKey = longPreferencesKey("LastNotify80GoalSentDate")

val lastNotify100GoalSentDateKey = longPreferencesKey("LastNotify100GoalSentDate")
