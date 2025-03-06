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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
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
import dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

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
    val bearName by viewModel.roadBearName.collectAsState(initial = "")
    var textFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(bearName) {
        textFieldValue = bearName
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            if (uri != null) {
                onImageSelected(uri)
            }
        }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier =
            Modifier
                .size(100.dp)
                .padding(8.dp)
                .clickable {
                    launcher.launch("image/*")
                }
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (profileImageUri != null) {
                Image(
                    painter =
                    rememberAsyncImagePainter(
                        model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(profileImageUri)
                            .build(),
                    ),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(100.dp),
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(100.dp),
                )
            }
        }
        Text(
            text = "プロフィール",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
        )
        TextField(
            value = textFieldValue,
            onValueChange = { newValue -> textFieldValue = newValue }
        )
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    viewModel.saveBearName(textFieldValue)
                    navController.popBackStack()
                }
            ) {
                Text("保存して戻る")
            }
        }
    }
}

class roadDataStoreProfile(application: Application) : AndroidViewModel(application) {
    val roadBearName: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[bearName] ?: ""
    }

    fun saveBearName(name: String) {
        viewModelScope.launch {
            getApplication<Application>().dataStore.edit { preferences ->
                preferences[bearName] = name
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
