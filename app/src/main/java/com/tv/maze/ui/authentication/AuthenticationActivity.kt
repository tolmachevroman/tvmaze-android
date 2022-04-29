package com.tv.maze.ui.authentication

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.*
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.tv.maze.R
import com.tv.maze.ui.authentication.viewmodels.AuthenticationViewModel
import com.tv.maze.ui.main.MainActivity
import com.tv.maze.ui.theme.TVmazeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TVmazeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AuthenticationNavigation(authenticationViewModel)
                }
            }
        }

        authenticationViewModel.createdPin.observe(this) { createdPin ->
            if (createdPin == null)
                return@observe

            if (createdPin) {
                finishAndNavigateToMain()
            } else {
                Toast.makeText(this, getString(R.string.pin_codes_do_not_match), Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Wait for Compose to render before showing biometric prompt
        Handler(Looper.getMainLooper()).postDelayed({
            if (checkBiometricSupport()) {
                buildBiometricPrompt()
            }
        }, 500)
    }

    private fun finishAndNavigateToMain() {
        startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
        finish()
    }

    /**
     * Biometric Authentication
     */

    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    println("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    println("Authentication Success!")
                    finishAndNavigateToMain()
                }
            }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager: KeyguardManager =
            getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isKeyguardSecure) {
            println("Fingerprint is not enabled in settings.")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            println("Fingerprint is not enabled in settings.")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            println("Authentication was cancelled by the user")
        }
        return cancellationSignal as CancellationSignal
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun buildBiometricPrompt() {
        val biometricPrompt: BiometricPrompt = BiometricPrompt.Builder(this@AuthenticationActivity)
            .setTitle(getString(R.string.biometric_prompt_title))
            .setSubtitle(getString(R.string.biometric_prompt_subtitle))
            .setNegativeButton(
                getString(android.R.string.cancel),
                this.mainExecutor,
                DialogInterface.OnClickListener { _, _ -> }).build()
        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
    }
}
