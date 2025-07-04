package org.nidkt.tekuteku.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PermissionAlert(modifier: Modifier = Modifier) {
    Row(modifier =modifier.background(color=Color(0xfffdeded), shape = RoundedCornerShape(8.dp)).padding(20.dp).fillMaxWidth() ) {
        Icon(Icons.Default.ErrorOutline,null, tint = Color(0xffd84644))
        Text("歩数の権限が許可されていません",color=Color(0xff5f2120))
    }
}

@Preview
@Composable
private fun PermissionAlertPreview() {
    PermissionAlert()
}
