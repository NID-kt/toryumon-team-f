package org.nidkt.tekuteku.initialFlow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.nidkt.tekuteku.ui.theme.GranulatedSugar
import org.nidkt.tekuteku.ui.theme.NuclearMango
import org.nidkt.tekuteku.ui.theme.RunningAvaterTheme

@Composable
fun BackButton(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = dropUnlessResumed {
            navController.popBackStack()
        },
        modifier =
            modifier
                .fillMaxWidth(),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = GranulatedSugar,
                contentColor = NuclearMango,
            ),
    ) {
        Text(
            text = "戻る",
            fontSize = 24.sp,
        )
    }
}

@Preview
@Composable
private fun BackbuttonPreview() {
    RunningAvaterTheme {
        BackButton(navController = rememberNavController())
    }
}
