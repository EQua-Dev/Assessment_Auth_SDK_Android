package tech.sourceid.assessment.authsdk.data

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


data class AuthConfig(
    val primaryColor: Color = Color(0xFF6200EE),
    val backgroundColor: Color = Color.White,
    val textColor: Color = Color.Black,
    val fontSize: TextUnit = 16.sp,
    val fontFamily: FontFamily = FontFamily.Default,
    val submitButtonText: String = "Submit",
    val showUsername: Boolean = true
)
