package tech.sourceid.assessment.authsdk.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import tech.sourceid.assessment.authsdk.data.UserData

object SecureStorageHelper {

    private const val PREFS_NAME = "secure_auth_prefs"

    fun storeUserData(context: Context, userData: UserData) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val securePrefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        with(securePrefs.edit()) {
            putString("email", userData.email)
            putString("password", userData.password)
            userData.username?.let { putString("username", it) }
            putString("firstName", userData.firstName)
            putString("lastName", userData.lastName)
            apply()
        }
    }
}
