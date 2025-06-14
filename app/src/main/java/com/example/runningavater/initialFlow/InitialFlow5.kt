@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.runningavater.initialFlow

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import bearName
import com.example.runningavater.R
import com.example.runningavater.initialFlow.components.BackButton
import com.example.runningavater.initialFlow.components.InitialFlowBackground
import com.example.runningavater.initialFlow.components.NextButton
import com.example.runningavater.startStepCounterService
import com.example.runningavater.ui.theme.GranulatedSugar
import com.example.runningavater.ui.theme.RunningAvaterTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dataStore
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("ComposeViewModelInjection")
@Composable
fun InitialFlow5Screen(navController: NavHostController) {
    val viewModel: YourViewModel = viewModel()
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val text =
        remember {
            mutableStateOf("")
        }
    val isError = text.value == ""

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val permissionState = rememberMultiplePermissionsState(
            listOf(Manifest.permission.ACTIVITY_RECOGNITION)
        ) {
            if (it.all { it.value }) {
                startStepCounterService(context)// 歩数の権限が許可された場合ステップカウンターサービス起動する処理
                // ここでは何もしない
            } else {
                // パーミッションが拒否された場合の処理
                // ここでは何もしない

            }
        }

        LaunchedEffect(Unit) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    InitialFlowBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "くまに名前を\nつけてあげましょう",
                    color = Color.Black,
                    fontSize = 32.sp,
                    lineHeight = 48.sp,
                    textAlign = TextAlign.Center,
                )
                Image(
                    painter = painterResource(id = R.drawable.initialflow56),
                    contentDescription = "くまちゃん",
                    modifier =
                        Modifier
                            .padding(
                                top = 20.dp,
                                bottom = 20.dp,
                            )
                            .size(200.dp)
                            .zIndex(-1f),
                )
                TextField(
                    value = text.value,
                    isError = isError,
                    onValueChange = { newValue -> text.value = newValue },
                    supportingText = {
                        if (isError) {
                            Text(
                                text = "何か名前をつけてあげてね",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    placeholder = { Text(text = "名前をここに入力してね") },
                    colors =
                        TextFieldDefaults.colors(
                            focusedContainerColor = GranulatedSugar,
                            unfocusedLabelColor = GranulatedSugar,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                    singleLine = true,
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                        ),
                    keyboardActions =
                        KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            },
                        ),
                )
                Text(
                    text = "※名前は後から変更できるよ",
                    modifier =
                        Modifier
                            .align(Alignment.End)
                            .padding(end = 20.dp),
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
                    nextDestination = "initialFlow/6/${text.value}",
                    modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 10.dp),
                    enabled = isError.not(),
                    onClick = {
                        viewModel.saveNameToDataStore(context, text.value)
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

// ViewModel内にDataStore書き込みの処理を追加
class YourViewModel : ViewModel() {

    fun saveNameToDataStore(
        context: Context,
        name: String,
    ) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[bearName] = name // 入力した文字列(name)を保存
            }
        }
    }
}


@Preview
@Composable
private fun InitialFlow5Preview() {
    RunningAvaterTheme {
        InitialFlow5Screen(rememberNavController())
    }
}
