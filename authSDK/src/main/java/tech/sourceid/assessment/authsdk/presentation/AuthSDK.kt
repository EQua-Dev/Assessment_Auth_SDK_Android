package tech.sourceid.assessment.authsdk.presentation

import android.content.Context
import android.content.Intent
import tech.sourceid.assessment.authsdk.AuthActivity
import tech.sourceid.assessment.authsdk.data.AuthConfig
import tech.sourceid.assessment.authsdk.data.AuthConfigHolder
import tech.sourceid.assessment.authsdk.data.UserData

object AuthSDK {
    fun launch(context: Context, config: AuthConfig, onSubmit: (UserData) -> Unit) {
        val intent = Intent(context, AuthActivity::class.java)
        // Pass config via Parcelable or static field
        AuthConfigHolder.config = config
        AuthConfigHolder.callback = onSubmit
        context.startActivity(intent)
    }
}


