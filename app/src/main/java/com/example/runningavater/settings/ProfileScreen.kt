package com.example.runningavater.settings

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import bearName
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.runningavater.R
import com.example.runningavater.ui.theme.SungYellow
import dataStore
import enthusiaasm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import userIcon
import userName

//@Composable
//fun ProfileScreen(navController: NavHostController) {
//    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
//    ProfileScreen(navController = navController, profileImageUri = profileImageUri) {
//    }
//}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileImageUri: Uri?,
    onImageSelected: (Uri) -> Unit,
    viewModel: roadDataStoreProfile = viewModel()
) {
    var selectedUserIcon by rememberSaveable { mutableStateOf("") }
    val userIcon by viewModel.roadUserIcon.collectAsState(initial = "")
    val bearName by viewModel.roadBearName.collectAsState(initial = "")
    var bearTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(bearName) {
        bearTextFieldValue = bearName
    }
    val userName by viewModel.roadUserName.collectAsState(initial = "")
    var userTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(userName) {
        userTextFieldValue = userName
    }
    val enth by viewModel.roadEnth.collectAsState(initial = "")
    var enthTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(enth) {
        enthTextFieldValue = enth
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            if (uri != null) {
                selectedUserIcon = uri.toString()
            }
        }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "プロフィールの編集",
            color = SungYellow,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
                .background(Color.White),
        )
        val iconToDisplay = if (selectedUserIcon.isNotEmpty()) {
            selectedUserIcon
        } else {
            userIcon // まだ新しいものを選んでいないときは、データストアに保存されている値
        }
        Box(
            modifier =
            Modifier
                .weight(1f, fill = false)
                .size(100.dp)
                .padding(8.dp)
                .clickable {
                    launcher.launch("image/*")
                },
            contentAlignment = Alignment.Center,
        ) {
            // userIconがnullまたは空のとき → initial_icon
            // そうでないとき → userIcon
            val painter = if (iconToDisplay.isEmpty()) {
                painterResource(id = R.drawable.initial_icon)
            } else {
                rememberAsyncImagePainter(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(iconToDisplay)
                        .build()
                )
            }
            Image(
                painter = painter,
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

        }

        TextField(
            value = bearTextFieldValue,
            onValueChange = { newValue -> bearTextFieldValue = newValue },
            label = { Text("くまの名前") }
        )
        TextField(
            modifier = Modifier
                .padding(top = 10.dp),
            value = userTextFieldValue,
            onValueChange = { newValue -> userTextFieldValue = newValue },
            label = { Text("あなたの名前") }
        )
        TextField(
            modifier = Modifier
                .padding(top = 10.dp),
            value = enthTextFieldValue,
            onValueChange = { newValue -> enthTextFieldValue = newValue },
            label = { Text("意気込み") }
        )
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.saveUserIcon(iconToDisplay)
                    viewModel.saveBearName(bearTextFieldValue)
                    viewModel.saveUserName(userTextFieldValue)
                    viewModel.saveEnthusiasm(enthTextFieldValue)
                    navController.popBackStack()
                }
            ) {
                Text("保存して戻る")
            }
        }
    }
}

class roadDataStoreProfile(application: Application) : AndroidViewModel(application) {
    val roadUserIcon: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[userIcon] ?: ""
    }
    val roadBearName: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[bearName] ?: ""
    }
    val roadUserName: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[userName] ?: ""
    }
    val roadEnth: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[enthusiaasm] ?: ""
    }

    fun saveUserIcon(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[userIcon] = name
            }
        }
    }

    fun saveBearName(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[bearName] = name
            }
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[userName] = name
            }
        }
    }

    fun saveEnthusiasm(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[enthusiaasm] = name
            }
        }
    }
}

//@Preview
//@Composable
//private fun ProfileScreenPreview() {
//    RunningAvaterTheme {
//        ProfileScreen(rememberNavController())
//    }
//}
