package com.example.runningavater.settings

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.ui.theme.GranulatedSugar
import dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import targetPeriod
import targetSteps

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("ComposeViewModelInjection", "ComposeUnstableReceiver")

@Composable
fun GoalSettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: roadDataStore = viewModel()
    val period by viewModel.roadPeriod.collectAsState(initial = "未設定")
    var periodTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(period) {
        periodTextFieldValue = period
    }
    val steps by viewModel.roadSteps.collectAsState(initial = "未設定")
    var stepsTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(steps) {
        stepsTextFieldValue = steps
    }
    val options = listOf("1日", "3日", "7日")
    var expanded by remember { mutableStateOf(false) }

    InitialFlowBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "目標歩数",
                    fontSize = 18.sp,
                    modifier =
                        Modifier
                            .padding(start = 20.dp),
                )
                TextField(
                    colors =
                        TextFieldDefaults.textFieldColors(
                            containerColor = GranulatedSugar,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 30.dp),
                    value = stepsTextFieldValue,
                    onValueChange = { newValue -> stepsTextFieldValue = newValue },
                    placeholder = { Text(text = "5000") },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                        ),
                )

                Text(
                    text = "目標期間",
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp),
                    fontSize = 18.sp,
                )
                ExposedDropdownMenuBox(
                    modifier =
                        Modifier
                            .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                ) {
                    TextField(
                        colors =
                            TextFieldDefaults.textFieldColors(
                                containerColor = GranulatedSugar,
                            ),
                        value = periodTextFieldValue,
                        onValueChange = { newValue -> periodTextFieldValue = newValue },
                        readOnly = true,
                        label = { Text("期間設定") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .menuAnchor() // メニューのアンカーを設定

                                .clickable { expanded = !expanded }, // フィールドをクリックしてメニューを展開
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    viewModel.savePeriod(option)
                                    expanded = false
                                },
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            viewModel.savePeriod(periodTextFieldValue)
                            viewModel.saveSteps(stepsTextFieldValue)
                            navController.popBackStack()
                        }
                    ) {
                        Text("保存して戻る")
                    }
                }
            }
        }
    }
}

class roadDataStore(application: Application) : AndroidViewModel(application) {

    val roadPeriod: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[targetPeriod] ?: ""

    }
    val roadSteps: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[targetSteps]?.toString() ?: ""

    }

    fun savePeriod(period: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[targetPeriod] = period
            }
        }
    }

    fun saveSteps(steps: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[targetSteps] = steps.toInt()
            }
        }
    }
}
