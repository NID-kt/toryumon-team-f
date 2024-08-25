package com.example.runningavater

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

@Composable
fun GoalSettingsScreen(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            DatePickerDialog(context, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                selectedDate = "${selectedYear}年 ${selectedMonth + 1}月 ${selectedDay}日"
            }, year, month, day).show()
        }) {
            Text(text = "歩数記録の開始日")
        }
        if (selectedDate.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "選択された日付: $selectedDate", fontSize = 18.sp)
        }
    }
}
