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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController

@Composable
fun LifecycleResumeEffect(onResume: () -> Unit) {
    // 現在の画面のLifecycleOwnerを取得
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        // LifecycleEventObserverでLifecycleのイベントを監視する
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // onResumeになったときに呼び出される
                onResume()
            }
        }
        // 監視を開始
        lifecycleOwner.lifecycle.addObserver(observer)

        // Composableが破棄されるときに呼び出される
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}


@Composable
fun AuthenticationScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false) }
    when {
        openAlertDialog.value -> {
            AuthenticationErrorDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "端末のパスワードを設定してください",
                dialogText = "本サービスは、端末のパスワードを設定していただくと利用可能になります。",
                icon = Icons.Default.Info
            )
        }
    }
    LifecycleResumeEffect {
        when (BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> { // 生体認証が利用可能
                showAuthenticationDialog(context, navController)
            }


            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> { // 生体情報が端末に登録されていない
                val enrollIntent =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                            putExtra(
                                Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                            )
                        }
                        context.startActivity(enrollIntent)
                    } else {
                        // Android 10 以下
                        showToast(context, "端末のパスワードを設定してください")
                        val securityIntent = Intent(Settings.ACTION_SECURITY_SETTINGS).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        context.startActivity(securityIntent)
                    }
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE, BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> { // 生体認証ハードウェアが利用不可
                showToast(context, "お使いの端末は対応本サービスを利用できません。")
            }

            else -> throw IllegalStateException("ここには入らないはず。")
        }
    }
    CircularProgressIndicator()
}

fun showToast(context: android.content.Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                        // これにより、「ホーム」に戻るときにバックスタックがクリアされる
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
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
