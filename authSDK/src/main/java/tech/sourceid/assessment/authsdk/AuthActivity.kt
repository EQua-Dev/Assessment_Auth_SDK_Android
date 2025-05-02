package tech.sourceid.assessment.authsdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import tech.sourceid.assessment.authsdk.data.AuthConfigHolder
import tech.sourceid.assessment.authsdk.presentation.AuthScreen

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AuthConfigHolder.config
        val callback = AuthConfigHolder.callback

        setContent {

            if (config != null) {
                AuthScreen(config = config, onSubmit = {
                    if (callback != null) {
                        callback(it)
                    }
                    finish() // Close after submit
                })
            }

        }
    }
}
