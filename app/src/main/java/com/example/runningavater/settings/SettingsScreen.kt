package com.example.runningavater.settings

import android.app.Application
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.runningavater.R
import com.example.runningavater.authentication.AuthenticationErrorDialog
import com.example.runningavater.ui.theme.GranulatedSugar
import com.example.runningavater.ui.theme.NuclearMango
import com.example.runningavater.ui.theme.RunningAvaterTheme
import com.example.runningavater.ui.theme.SungYellow
import dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import bearName
import userName
import userIcon

@Composable
fun SettingsScreen(
    navController: NavHostController,
    profileImageUri: Uri?,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = viewModel(),
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    val userName by viewModel.roadUserName.collectAsState(initial = "未設定")
    val userIcon by viewModel.roadUserIcon.collectAsState(initial = "")
    val bearName by viewModel.roadBearName.collectAsState(initial = "未設定")
    val iconToDisplay = userIcon
    var bearTextFieldValue by rememberSaveable { mutableStateOf("") }
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
    LaunchedEffect(bearName) {
        bearTextFieldValue = bearName
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
        modifier
            .fillMaxSize()
            .background(SungYellow)
            .padding(top = 46.dp)
    ) {
        when {
            openAlertDialog.value -> {
                GoalSettingDialog(
                    onDismissRequest = { openAlertDialog.value = false },
                    onConfirmation = {
                        openAlertDialog.value = false
                        println("Confirmation registered") // Add logic here to handle confirmation.
                    },
                )
            }
        }
        Text(
            text = "設定",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = NuclearMango,
            modifier =
            Modifier
                .padding(bottom = 40.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(GranulatedSugar)
                .padding(horizontal = 22.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        Row(
            modifier =
            Modifier
                .padding(
                    vertical = 10.dp,
                    horizontal = 5.dp
                )
                .fillMaxWidth()
                .clickable(onClick = { navController.navigate("settings/profile") })
                .clip(RoundedCornerShape(20.dp))
                .background(GranulatedSugar, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .size(70.dp)
            ) {
//                if (profileImageUri != null) {
//                    Image(
//                        painter =
//                        rememberAsyncImagePainter(
//                            model =
//                            ImageRequest
//                                .Builder(LocalContext.current)
//                                .data(profileImageUri)
//                                .build(),
//                        ),
//                        contentDescription = "Profile Image",
//                        modifier = Modifier.size(48.dp),
//                    )
//                } else {
                Image(
                    painter = painter,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier =
                    Modifier.size(70.dp)
                            .clip(CircleShape)
                            .size(150.dp)
                )
//                }
            }
            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    text = userName,
                    fontSize = 32.sp,
                    color = NuclearMango,
                    modifier =
                    Modifier
                        .padding(start = 20.dp)
                )
                Text(
                    text = bearName,
                    fontSize = 18.sp,
                    color = NuclearMango,
                    modifier =
                    Modifier
                        .padding(start = 20.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Profile",
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(40.dp),
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 33.dp,
                    horizontal = 5.dp
                )
                .clip(RoundedCornerShape(16.dp))
                .background(GranulatedSugar)
                .padding(vertical = 10.dp)
                .clickable { openAlertDialog.value = true }
        ) {
            Text(
                "目標歩数設定",
                fontSize = 18.sp,
                color = NuclearMango,
                modifier =
                Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
            )
        }
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 33.dp,
                    horizontal = 5.dp
                )
                .clip(RoundedCornerShape(16.dp))
                .background(GranulatedSugar)
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "通知設定",
                fontSize = 18.sp,
                color = NuclearMango,
                modifier =
                Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.padding(end = 16.dp),
            )
        }
    }
}
class SettingViewModel(application: Application) : AndroidViewModel(application) {
    val roadUserIcon: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[userIcon] ?: ""
    }
    val roadBearName: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[bearName] ?: ""
    }
    val roadUserName: Flow<String> = getApplication<Application>().dataStore.data.map { preferences ->
        preferences[userName] ?: ""
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    RunningAvaterTheme {
        SettingsScreen(navController = rememberNavController(), profileImageUri = null)
    }

}
