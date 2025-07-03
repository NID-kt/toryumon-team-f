package org.nidkt.tekuteku.initialFlow

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dataStore
import kotlinx.coroutines.launch
import org.nidkt.tekuteku.R
import org.nidkt.tekuteku.initialFlow.components.BackButton
import org.nidkt.tekuteku.initialFlow.components.InitialFlowBackground
import org.nidkt.tekuteku.initialFlow.components.NextButton
import org.nidkt.tekuteku.ui.theme.GranulatedSugar
import org.nidkt.tekuteku.ui.theme.RunningAvaterTheme
import userIcon
import userName

@SuppressLint("ComposeViewModelInjection")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialFlow7Screen(navController: NavHostController) {
    val viewModel: UserProfileViewModel = viewModel()
    val context = LocalContext.current
    val text = remember { mutableStateOf("") }
    val isError = text.value == ""
    var imageUri: Uri? by remember {
        mutableStateOf(null)
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            imageUri = uri
        }
    InitialFlowBackground {
        Box(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(0.dp, 50.dp, 0.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "あなたの名前とアイコン",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                Text(
                    text = "を設定しよう！",
                    color = Color.Black,
                    fontSize = 32.sp,
                )
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .offset((0.dp), (35.dp))
                                .size(220.dp)
                                .clickable { launcher.launch("image/%") },
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.initial_icon),
                        contentDescription = "初期アイコン",
                        modifier =
                            Modifier
                                .offset((0.dp), (35.dp))
                                .size(220.dp)
                                .clip(CircleShape)
                                .clickable { launcher.launch("image/%") },
                    )
                }
                TextField(
                    isError = isError,
                    value = text.value,
                    onValueChange = { newValue -> text.value = newValue },
                    supportingText = {
                        if (isError) {
                            Text(
                                text = "あなたの名前を入力してね",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
//                    placeholder = { Text(text = "名前をここに入力してね") },
                    colors =
                        TextFieldDefaults.colors(
                            focusedContainerColor = GranulatedSugar,
                            unfocusedLabelColor = GranulatedSugar,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 40.dp),
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
                    text = "※名前とアイコンは",
                    modifier =
                        Modifier
                            .align(Alignment.End)
                            .padding(end = 100.dp),
                )
                Text(
                    text = "後から変更できるよ",
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
                        .padding(20.dp, 0.dp, 20.dp, 90.dp),
            ) {
                NextButton(
                    enabled = isError.not(),
                    navController = navController,
                    nextDestination = "initialFlow/8",
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.saveNameToDateStore(context, text.value)
                            imageUri?.let {
                                viewModel.saveUserIconToDateStore(context, it.toString())
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

class UserProfileViewModel : ViewModel() {

    fun saveNameToDateStore(
        context: Context,
        name: String,
    ) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[userName] = name
            }
        }
    }

    fun saveUserIconToDateStore(
        context: Context,
        usericon: String,
    ) {
        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[userIcon] = usericon
            }
        }
    }
}

@Preview
@Composable
private fun InitialFlow7Preview() {
    RunningAvaterTheme {
        InitialFlow7Screen(rememberNavController())
    }
}
