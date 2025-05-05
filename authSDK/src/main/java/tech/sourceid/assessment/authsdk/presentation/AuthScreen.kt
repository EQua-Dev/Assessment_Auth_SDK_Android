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

    val emailError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val usernameError = remember { mutableStateOf<String?>(null) }

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(config.backgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        // EMAIL
        Text("Email", color = config.textColor)
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = null
            },
            placeholder = { Text("Enter your email") },
            isError = emailError.value != null,
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )
        emailError.value?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        // PASSWORD
        Text("Password", color = config.textColor, modifier = Modifier.padding(top = 8.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = null
            },
            placeholder = { Text("Minimum 8 characters, alphanumeric") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError.value != null,
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )
        passwordError.value?.let {
            Text(it, color = Color.Red, fontSize = 12.sp)
        }

        // USERNAME (OPTIONAL)
        if (config.showUsername) {
            Text("Username", color = config.textColor, modifier = Modifier.padding(top = 8.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = {
                    username.value = it
                    usernameError.value = null
                },
                placeholder = { Text("No spaces or special characters") },
                isError = usernameError.value != null,
                textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
                modifier = Modifier.fillMaxWidth()
            )
            usernameError.value?.let {
                Text(it, color = Color.Red, fontSize = 12.sp)
            }
        }

        // FIRST NAME
        Text("First Name", color = config.textColor, modifier = Modifier.padding(top = 8.dp))
        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            placeholder = { Text("Enter your first name") },
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        // LAST NAME
        Text("Last Name", color = config.textColor, modifier = Modifier.padding(top = 8.dp))
        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            placeholder = { Text("Enter your last name") },
            textStyle = TextStyle(color = config.textColor, fontSize = config.fontSize, fontFamily = config.fontFamily),
            modifier = Modifier.fillMaxWidth()
        )

        // SUBMIT BUTTON
        Button(
            onClick = {
                var hasError = false

                val emailPattern = Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$")
                if (!emailPattern.matches(email.value)) {
                    emailError.value = "Invalid email format"
                    hasError = true
                }

                val passwordPattern = Regex("^(?=.*[a-zA-Z])(?=.*\\d).{8,}$")
                if (!passwordPattern.matches(password.value)) {
                    passwordError.value = "Password must be at least 8 characters and alphanumeric"
                    hasError = true
                }

                if (config.showUsername && !Regex("^[a-zA-Z0-9_]+$").matches(username.value)) {
                    usernameError.value = "Username must not contain spaces or special characters"
                    hasError = true
                }

                if (!hasError) {
                    val user = UserData(
                        email.value,
                        password.value,
                        if (config.showUsername) username.value else null,
                        firstName.value,
                        lastName.value
                    )
                    onSubmit(user)
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
