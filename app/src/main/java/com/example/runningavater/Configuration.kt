import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.runningavater.R

@Composable
fun SettingPage() {
    val navController = rememberNavController()
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    NavHost(navController = navController, startDestination = "settings") {
        composable("settings") { SettingsScreen(navController, profileImageUri) }
        composable("profile") { ProfileScreen(navController, profileImageUri) { profileImageUri = it } }
        composable("goal_settings") { GoalSettingsScreen() }
        composable("span_settings") { SpanSettingsScreen() }
    }
}

@Composable
fun ProfileScreen(navController: NavHostController, profileImageUri: Uri?, onImageSelected: (Uri) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            onImageSelected(uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
                .clickable {
                    launcher.launch("image/*")
                }
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profileImageUri)
                            .build()
                    ),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
        Text(
            text = "プロフィール",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpanSettingsScreen() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("1日") }
    val options = listOf("1日", "3日", "7日")

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "期間設定", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = {},
                readOnly = true,
                label = { Text("期間設定") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor() // メニューのアンカーを設定
                    .clickable { expanded = !expanded } // フィールドをクリックしてメニューを展開
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GoalSettingsScreen() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "歩数設定", fontSize = 24.sp)
        // 目標設定画面の他のUI要素をここに追加できます
    }
}

@Composable
fun SettingsScreen(navController: NavHostController, profileImageUri: Uri?) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "設定画面",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        ProfileSection(navController, profileImageUri)
        GoalSettingsSection(navController)
        SpanSettingsSection(navController)
        NotificationSettingsSection()
    }
}

@Composable
fun GoalSettingsSection(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("goal_settings") })
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "歩数設定",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
    }
}

@Composable
fun SpanSettingsSection(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("span_settings") })
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "期間設定",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
    }
}

@Composable
fun NotificationSettingsSection() {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "通知設定",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun ProfileSection(navController: NavHostController, profileImageUri: Uri?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("profile") })
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
                .background(Color.Gray, shape = CircleShape)
        ) {
            if (profileImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(profileImageUri)
                            .build()
                    ),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(48.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.test),
                    contentDescription = "Profile Image",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Text(
            text = "プロフィール",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)
        )
    }
}
