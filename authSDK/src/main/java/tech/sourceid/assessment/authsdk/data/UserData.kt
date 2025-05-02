package tech.sourceid.assessment.authsdk.data

data class UserData(
    val email: String,
    val password: String,
    val username: String?,
    val firstName: String,
    val lastName: String
)
