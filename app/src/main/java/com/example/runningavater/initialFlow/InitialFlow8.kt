@file:Suppress("DEPRECATION")

package com.example.runningavater.initialFlow

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.runningavater.AggregateSteps
import com.example.runningavater.MainApplication
import com.example.runningavater.aggregateDays
import com.example.runningavater.db.StepDate
import com.example.runningavater.initialFlow.components.BackButton
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.ui.theme.GranulatedSugar
import com.example.runningavater.ui.theme.RunningAvaterTheme
import dataStore
import isInit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import targetPeriod
import targetSteps
import java.util.concurrent.TimeUnit

@SuppressLint("ComposeViewModelInjection")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialFlow8Screen(navController: NavHostController) {
    var selectedOption by remember { mutableStateOf("1日") }
    val options = listOf("1日", "3日", "7日")
    var expanded by remember { mutableStateOf(false) }
    val viewModel: TargetSettings = viewModel()
    val context = LocalContext.current
    val text = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    InitialFlowBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                Modifier
                    .fillMaxSize(),
            ) {
                Text(
                    text = "あなたの目標歩数と\n期間を設定しよう！",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 46.3.sp,
                    modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = 48.dp,
                            bottom = 50.dp,
                        ),
                )
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
                    value = text.value,
                    onValueChange = { newValue -> text.value = newValue },
                    placeholder = { Text(text = "5000") },
                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,
                        ),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            },
                        ),
                    singleLine = true,
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
                        value = selectedOption,
                        onValueChange = { newValue -> selectedOption = newValue },
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
                                    selectedOption = option
                                    expanded = false
                                },
                            )
                        }
                    }
                }
                Text(
                    text = "※目標歩数と目標期限は\n後からでも変更できるよ",
                    fontSize = 18.sp,
                    modifier =
                    Modifier
                        .align(Alignment.End)
                        .padding(0.dp, 10.dp, 10.dp, 0.dp),
                )
            }

            Column(
                modifier =
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(0.dp, 0.dp, 0.dp, 80.dp),
            ) {
                NextButton(
                    navController = navController,
                    nextDestination = "initialFlow/9",
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                    onClick = {
                        viewModel.viewModelScope.launch(Dispatchers.IO){
                            viewModel.savePeriodToDataStore(context, selectedOption)
                            viewModel.saveStepsToDataStore(context, text.value)

                            if (!(context.dataStore.data.first()[isInit] ?: false)) {





                                val aggregateSteps = OneTimeWorkRequestBuilder<AggregateSteps>()
                                    .setInitialDelay(aggregateDays(2).toLong(), TimeUnit.DAYS)
                                    .build()
                                WorkManager.getInstance(context).enqueue(aggregateSteps)
                                context.dataStore.edit { settings ->
                                    settings[isInit] = true
                                }
                            }
                        }
                    },
                )
            }
            Column(
                modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
            ) {
                BackButton(
                    navController = navController,
                )
            }
        }
    }
}

class TargetSettings : ViewModel() {


    fun savePeriodToDataStore(
        context: Context,
        name: String,
    ) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[targetPeriod] = name // 入力した文字列(name)を保存
            }
        }
    }

    fun saveStepsToDataStore(
        context: Context,
        name: String,
    ) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[targetSteps] = name.toInt()
            }
        }
    }
}

@Preview
@Composable
private fun InitialFlow8Preview() {
    RunningAvaterTheme {
        InitialFlow8Screen(
            rememberNavController(),
        )
    }
}
