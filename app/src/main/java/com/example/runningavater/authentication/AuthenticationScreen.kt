package com.example.runningavater.authentication

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun AuthenticationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        when (BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> { // 生体認証が利用可能
                showAuthenticationDialog(context, navController)
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> { // 生体情報が端末に登録されていない
                val enrollIntent =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                BIOMETRIC_STRONG or DEVICE_CREDENTIAL,
                            )
                        }
                    } else {
                        TODO("VERSION.SDK_INT < R")
                    }
                context.startActivity(enrollIntent)
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE, BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> { // 生体認証ハードウェアが利用不可
                TODO("ダイアログを表示")
            }

            else -> throw IllegalStateException("ここには入らないはず。")
        }
    }
    CircularProgressIndicator()
}

fun showAuthenticationDialog(
    context: Context,
    navController: NavController,
) {
    val biometricPrompt =
        BiometricPrompt(
            context as FragmentActivity,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast
                        .makeText(
                            context,
                            "Authentication error: $errString",
                            Toast.LENGTH_SHORT,
                        ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    navController.navigate("home") {
                        // Optional: これにより、「ホーム」に戻るときにバックスタックがクリアされる
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true // 同じ画面を複数スタックしない
                        restoreState = true // 前の状態を復元
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast
                        .makeText(
                            context,
                            "Authentication failed",
                            Toast.LENGTH_SHORT,
                        ).show()
                }
            },
        )

    val promptInfo =
        BiometricPrompt.PromptInfo
            .Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
            .build()
    biometricPrompt.authenticate(promptInfo)
}
