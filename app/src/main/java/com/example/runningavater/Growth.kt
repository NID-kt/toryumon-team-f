import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun GrowthScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "成長記録",
            fontSize = 20.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .weight(1f) // Use weight to allow the PieChart to take remaining space
                .padding(bottom = 16.dp) // Add bottom padding to separate from buttons
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            PieChart(
                modifier = Modifier
                    .size(300.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {  }, modifier = Modifier.padding(8.dp)) {
                Text("Start")
            }
            Button(onClick = {  }, modifier = Modifier.padding(8.dp)) {
                Text("Stop")
            }
        }
    }
}

@Composable
fun PieChart(modifier: Modifier = Modifier) {
    val pieEntryList = listOf(
        PieEntry(80f, ""),
        PieEntry(20f, "")
    )
    val pieDataSet = PieDataSet(pieEntryList, "").apply {
        colors = listOf(Color.Green, Color.Red).map { it.toArgb() }
    }
    AndroidView(
        factory = { context ->
            com.github.mikephil.charting.charts.PieChart(context).apply {
                description = Description().apply { text = "" }
                centerText = "達成度"
                setEntryLabelTextSize(11f)
                data = PieData(pieDataSet).apply { setValueTextSize(20f) }
                animateXY(1000, 1000)
                legend.isEnabled = false
                description.isEnabled = false
                invalidate()
            }
        },
        modifier = modifier
    )
}





/*@Composable
fun PieChart(modifier: Modifier = Modifier) {
    //円グラフの値を入力
    val pieEntryList = listOf(
        PieEntry(80f, ""),
        PieEntry(20f, "")
    )
    //PieEntryに入力した値をDateSetに入力
    val pieDataSet = PieDataSet(pieEntryList, "").apply {
        colors = listOf(Color.Green, Color.Red).map { it.toArgb() }
    }
    AndroidView(
        factory = { context ->
            com.github.mikephil.charting.charts.PieChart(context).apply {
                description = Description().apply { text = "" }
                centerText = "達成度"
                setEntryLabelTextSize(11f)
                data = PieData(pieDataSet).apply { setValueTextSize(20f) }
                // アニメーションを指定
                animateXY(1000, 1000)
                //判例を非表示
                legend.isEnabled = false
                //説明ラベルの非表示
                description.isEnabled = false
                invalidate()
            }
        },
        modifier = modifier
    )
}*/



