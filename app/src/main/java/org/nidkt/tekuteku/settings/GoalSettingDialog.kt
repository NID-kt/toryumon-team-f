package org.nidkt.tekuteku.settings

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.nidkt.tekuteku.ui.theme.GranulatedSugar
import targetSteps

@Composable
fun GoalSettingDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    viewModel: GoalSettingViewModel = viewModel(),
) {
    val bearName by viewModel.roadTargetSteps.collectAsState(initial = "未設定")
    var bearTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(bearName) {
        bearTextFieldValue = bearName
    }
    AlertDialog(
        containerColor = Color.White,
        title = {
            Text("目標歩数設定")
        },
        text = {
            Column {
                Text("1日の目標歩数を入力してね")
                TextField(
                    colors =
                        TextFieldDefaults.colors(
                            focusedContainerColor = GranulatedSugar,
                            unfocusedContainerColor = GranulatedSugar,
                        ),
                    value = bearTextFieldValue,
                    onValueChange = { newValue ->
                        bearTextFieldValue = newValue
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },

        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.saveTargetSteps(bearTextFieldValue)
                    onConfirmation()
                }
            ) {
                Text("保存して閉じる")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("閉じる")
            }
        }
    )
}

class GoalSettingViewModel(application: Application) : AndroidViewModel(application) {
    val roadTargetSteps: Flow<String> =
        getApplication<Application>().dataStore.data.map { preferences ->
            preferences[targetSteps]?.toString() ?: ""
        }

    fun saveTargetSteps(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[targetSteps] = name.toInt()
            }
        }
    }

}
