package com.example.runningavater.settings

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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.runningavater.R

@Composable
fun SettingsScreen(
    navController: NavHostController,
    profileImageUri: Uri?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .padding(16.dp)
                .fillMaxSize(),
    ) {
        Text(
            text = "設定画面",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier =
                Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
        )
        ProfileSection(navController, profileImageUri)
        GoalSettingsSection(navController)
        SpanSettingsSection(navController)
        NotificationSettingsSection()
    }
}

@Composable
fun GoalSettingsSection(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = { navController.navigate("settings/goalsettings") })
                .padding(vertical = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "歩数設定",
            fontSize = 20.sp,
            modifier =
                Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
        )
    }
}

@Composable
fun SpanSettingsSection(navController: NavHostController) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = { navController.navigate("settings/spansettings") })
                .padding(vertical = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "期間設定",
            fontSize = 20.sp,
            modifier =
                Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
        )
    }
}

@Composable
fun NotificationSettingsSection() {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "通知設定",
            fontSize = 18.sp,
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

@Composable
fun ProfileSection(
    navController: NavHostController,
    profileImageUri: Uri?,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = { navController.navigate("settings/profile") })
                .padding(vertical = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier =
                Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .background(Color.Gray, shape = CircleShape),
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
                    modifier = Modifier.size(48.dp),
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(48.dp),
                )
            }
        }
        Text(
            text = "プロフィール",
            fontSize = 20.sp,
            modifier =
                Modifier
                    .padding(start = 20.dp)
                    .weight(1f),
        )
    }
}
