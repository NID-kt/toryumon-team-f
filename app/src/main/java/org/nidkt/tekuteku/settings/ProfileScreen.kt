package org.nidkt.tekuteku.settings

import android.app.Application
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import dataStore
import enthusiaasm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.nidkt.tekuteku.R
import org.nidkt.tekuteku.authentication.AuthenticationErrorDialog
import org.nidkt.tekuteku.ui.theme.NuclearMango
import org.nidkt.tekuteku.ui.theme.SungYellow
import userIcon
import userName

@Composable
fun ProfileScreen(navController: NavHostController) {
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    ProfileScreen(navController = navController, profileImageUri = profileImageUri) {
        profileImageUri = it
    }
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileImageUri: Uri?,
    viewModel: ProfileViewModel = viewModel(),
    onImageSelected: (Uri) -> Unit,
) {
    var selectedUserIcon by rememberSaveable { mutableStateOf("") }
    val userIcon by viewModel.roadUserIcon.collectAsState(initial = "")
    val bearName by viewModel.roadBearName.collectAsState(initial = "未設定")
    var bearTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(bearName) {
        bearTextFieldValue = bearName
    }
    val userName by viewModel.roadUserName.collectAsState(initial = "未設定")
    var userTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(userName) {
        userTextFieldValue = userName
    }
    val enth by viewModel.roadEnth.collectAsState(initial = "意気込みを入力してね")
    var enthTextFieldValue by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(enth) {
        enthTextFieldValue = enth
    }
    val openAlertDialog = remember { mutableStateOf(false) }
    when {
        openAlertDialog.value -> {
            AuthenticationErrorDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Alert dialog example",
                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info
            )
        }
    }
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
        ) { uri: Uri? ->
            if (uri != null) {
                selectedUserIcon = uri.toString()
            }
        }
    val iconToDisplay = if (selectedUserIcon.isNotEmpty()) {
        selectedUserIcon
    } else {
        userIcon // まだ新しいものを選んでいないときは、データストアに保存されている値
    }
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
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = SungYellow)
                .safeDrawingPadding()
                .padding(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "プロフィールの編集",
                color = NuclearMango,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .background(Color.White),
            )
        }

        Image(
            painter = painter,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    launcher.launch("image/*")
                }
                .clip(CircleShape)
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "お名前",
                color = NuclearMango
            )
            TextField(
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(4.dp)),
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
                value = userTextFieldValue,
                onValueChange = { newValue -> userTextFieldValue = newValue },
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "くまの\nお名前",
                color = NuclearMango

            )
            TextField(
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(4.dp)),
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
                value = bearTextFieldValue,
                onValueChange = { newValue -> bearTextFieldValue = newValue },
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.White)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                "意気込み",
                modifier = Modifier
                    .padding(top = 15.dp),
                color = NuclearMango

            )
            TextField(
                colors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                modifier = Modifier
                    .fillMaxSize()
                    .height(200.dp)
                    .clip(RoundedCornerShape(4.dp)),
                value = enthTextFieldValue,
                onValueChange = { newValue -> enthTextFieldValue = newValue },
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
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

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
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
