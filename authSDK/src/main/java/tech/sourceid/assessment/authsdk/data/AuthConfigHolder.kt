package tech.sourceid.assessment.authsdk.data

object AuthConfigHolder {
    var config: AuthConfig? = AuthConfig()
    var callback: ((UserData) -> Unit)? = null
}
