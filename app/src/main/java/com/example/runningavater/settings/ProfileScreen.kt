package com.example.runningavater.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
    onImageSelected: (Uri) -> Unit,
) {
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
    }
}
