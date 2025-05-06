package com.example.runningavater.growth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.runningavater.MainApplication
import com.example.runningavater.home.toEpochMillis
import com.example.runningavater.ui.theme.GranulatedSugar
import com.example.runningavater.ui.theme.NuclearMango
import com.example.runningavater.ui.theme.SungYellow
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import dataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import targetSteps
import java.time.LocalDateTime

@Composable
fun GrowthScreen() {
    Column(
        modifier =
            Modifier
                // .padding(16.dp)
                .background(Color(0xFFF8E9C3))
                .fillMaxSize(),
    ) {
        Text(
            text = "今日の達成率",
            fontSize = 30.sp,
            modifier =
                Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
        )
        Box(
            modifier =
                Modifier
                    .weight(1f) // Use weight to allow the PieChart to take remaining space
                    .padding(bottom = 16.dp) // Add bottom padding to separate from buttons
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            PieChart(
                modifier =
                    Modifier
                        .size(300.dp),
            )
        }
    }
}

@Composable
fun PieChart(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val stepcount = remember { mutableStateOf<Int?>(null) }
    val targetstepscount = remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(key1 = Unit) {
        val now = LocalDateTime.now() // 2025/02/23 23:52:10.123

        val todayStart =
            now
                .withHour(0) // 2025/02/23 00:52:10.123
                .withMinute(0) // 2025/02/23 00:00:10.123
                .withSecond(0) // 2025/02/23 00:00:00.123
                .withNano(0) // 2025/02/23 00:00:00.000000

        val todayEnd =
            now
                .withHour(23) // 2025/02/23 00:52:10.123
                .withMinute(59) // 2025/02/23 00:00:10.123
                .withSecond(59) // 2025/02/23 00:00:00.123
                .withNano(999999999) // 2025/02/23 00:00:00.000000
        launch(Dispatchers.IO) {
            val app = context.applicationContext as MainApplication
            stepcount.value = app.db.stepDateDao().getTotalWalk(todayStart.toEpochMillis(), todayEnd.toEpochMillis())
            targetstepscount.value = app.dataStore.data.first()[
                targetSteps
            ]

        }
    }
    if (targetstepscount.value == null) {
        return
    }
    val pieEntryList =
        listOf(
            PieEntry((stepcount.value?.toFloat()?: 0f)/targetstepscount.value!!*100, "","達成率"),
            PieEntry(100 - (stepcount.value?.toFloat()?: 0f)/targetstepscount.value!!*100, "","未達成率"),
        )

    val pieDataSet =
        PieDataSet(pieEntryList, "").apply {
            colors = listOf(NuclearMango, GranulatedSugar).map { it.toArgb() }
        }
    AndroidView(
        factory = { context ->
            com.github.mikephil.charting.charts.PieChart(context).apply {
                description = Description().apply { text = "" }
                centerText = "達成度"
                setCenterTextSize(18f)
                setCenterTextColor(NuclearMango.toArgb())
                setEntryLabelTextSize(11f)
                data = PieData(pieDataSet).apply { setValueTextSize(20f)
                setValueFormatter(object : ValueFormatter() {
                    override fun getPieLabel(value: Float, pieEntry: PieEntry?): String {
                        if (pieEntry?.data == "達成率"){
                            return "${value}%"
                        }
                        return ""
                    }
                })}
                // アニメーションを指定
                animateXY(1000, 1000)
                // 判例を非表示
                legend.isEnabled = false
                // 説明ラベルの非表示
                description.isEnabled = false
                invalidate()
            }
        },
        modifier = modifier.run { size(100.dp) },
    )
}
