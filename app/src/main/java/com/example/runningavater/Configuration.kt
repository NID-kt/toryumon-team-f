import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.runningavater.R

@Composable
fun MyApp(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "settings") {
        composable("settings") { SettingsScreen(navController) }
        composable("profile") { ProfileScreen() }
        composable("goal_settings") { GoalSettingsScreen() }
        composable("span_settings"){SpanSettingsScreen()}
    }
}

@Composable
fun ProfileScreen(){
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "プロフィール設定", fontSize = 24.sp)
        // プロフィール画面の他のUI要素をここに追加できます
    }

}

@Composable
fun SpanSettingsScreen(){
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "期間設定", fontSize = 24.sp)
        // 目標設定画面の他のUI要素をここに追加できます
    }
}

@Composable
fun GoalSettingsScreen(){
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "歩数設定", fontSize = 24.sp)
        // 目標設定画面の他のUI要素をここに追加できます
    }
}
@Composable
fun SettingsScreen(navController: NavHostController) {
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
        ProfileSection(navController)
        GoalSettingsSection(navController)
        SpanSettingsSection(navController)
        NotificationSettingsSection()
    }
}

@Composable
fun GoalSettingsSection(navController: NavHostController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("goal_settings")  })
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
fun SpanSettingsSection(navController: NavHostController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("span_settings")  })
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
fun NotificationSettingsSection(){
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
fun ProfileSection(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navController.navigate("profile") })
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.test),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp)
        )
        Text(
            text = "プロフィール",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 20.dp)
                .weight(1f)
        )

    }
}