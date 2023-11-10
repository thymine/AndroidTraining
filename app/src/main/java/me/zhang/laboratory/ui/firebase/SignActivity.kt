package me.zhang.laboratory.ui.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import me.zhang.laboratory.R

private const val TAG = "SignActivity"

class SignActivity : AppCompatActivity() {

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Log.d(TAG, "onSignInResult:$response")
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            Log.d(TAG, "onSignInResult: $user")

            Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Main() }
    }

    @Composable
    fun Main() {
        Box {
            val scrollableState = rememberScrollableState { 0f }
            Column(Modifier.scrollable(scrollableState, Orientation.Vertical)) {
                Button(onClick = {
                    // Choose authentication providers
                    val providers = arrayListOf(
                        AuthUI.IdpConfig.EmailBuilder().build(),
                        AuthUI.IdpConfig.PhoneBuilder().build(),
                        AuthUI.IdpConfig.GoogleBuilder().build(),
//            AuthUI.IdpConfig.FacebookBuilder().build(),
//            AuthUI.IdpConfig.TwitterBuilder().build(),
                    )

                    // Create and launch sign-in intent
                    val signInIntent = AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_google_light_normal_icon) // Set logo drawable
                        .setTheme(R.style.MySuperAppTheme) // Set theme
                        .setTosAndPrivacyPolicyUrls(
                            "https://example.com/terms.html",
                            "https://example.com/privacy.html",
                        )
                        .build()
                    signInLauncher.launch(signInIntent)
                }) {
                    Text("Sign in")
                }

                var email by rememberSaveable { mutableStateOf("zhanglts@163.com") }
                OutlinedTextField(
                    value = email,
                    label = {
                        Text("Email")
                    }, onValueChange = {
                        email = it
                    })

                Button(onClick = {
                    val actionCodeSettings = actionCodeSettings {
                        // URL you want to redirect back to. The domain (www.example.com) for this
                        // URL must be whitelisted in the Firebase Console.
                        url = "https://www.example.com/finishSignUp?cartId=1234"
                        // This must be true
                        handleCodeInApp = true
//                        iosBundleId = "com.example.ios"
                        setAndroidPackageName(
                            application.packageName,
                            true, // installIfNotAvailable
                            "1", // minimumVersion
                        )
                    }

                    Firebase.auth.sendSignInLinkToEmail(email, actionCodeSettings)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                Log.d(TAG, "Email sent.")
                                Toast.makeText(this@SignActivity, "Email sent.", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Log.w(TAG, "Failed to send email.", task.exception)
                                Toast.makeText(
                                    this@SignActivity,
                                    "Failed to send email.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }) {
                    Text("Email link Authentication")
                }

                Button(onClick = {
                    AuthUI.getInstance()
                        .signOut(this@SignActivity)
                        .addOnCompleteListener {
                            Toast.makeText(this@SignActivity, "Signed out", Toast.LENGTH_SHORT)
                                .show()
                        }
                }) {
                    Text("Sign out")
                }

                Button(onClick = {
                    AuthUI.getInstance()
                        .delete(this@SignActivity)
                        .addOnCompleteListener {
                            Toast.makeText(this@SignActivity, "Deleted user", Toast.LENGTH_SHORT)
                                .show()
                        }
                }) {
                    Text("Delete user")
                }
            }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun PreviewMain() {
        Main()
    }
}