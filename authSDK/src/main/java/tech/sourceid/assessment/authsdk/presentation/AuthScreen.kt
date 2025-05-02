package tech.sourceid.assessment.authsdk.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.sourceid.assessment.authsdk.data.AuthConfig
import tech.sourceid.assessment.authsdk.data.UserData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    config: AuthConfig,
    onSubmit: (UserData) -> Unit
) {

    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }

    val errorMessage = remember { mutableStateOf<String?>(null) }
    val interactionSource = remember { MutableInteractionSource() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(config!!.backgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email", color = config.textColor) },
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password", color = config.textColor) },
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        if (config.showUsername) {
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username", color = config.textColor) },
                textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
                modifier = Modifier.fillMaxWidth()
            )
        }

        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name", color = config.textColor) },
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name", color = config.textColor) },
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage.value?.let {
            Text(it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))
        }

        Button(
            onClick = {
                val emailPattern = Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$")
                if (!emailPattern.matches(email.value)) {
                    errorMessage.value = "Invalid email format"
                } else if (password.value.length < 8) {
                    errorMessage.value = "Password must be at least 8 characters"
                } else {
                    errorMessage.value = null
                    val user = UserData(
                        email.value,
                        password.value,
                        if (config.showUsername) username.value else null,
                        firstName.value,
                        lastName.value
                    )
                    // Store securely
//                    SecureStorageHelper.storeUserData(context, user)

                    onSubmit(
                        user
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = config.primaryColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            interactionSource = interactionSource
        ) {
            Text(text = config.submitButtonText, color = Color.White)
        }
    }
}
