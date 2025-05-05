# 🚀 AuthSDK

**AuthSDK** is a Swift-based authentication SDK that offers a customizable UI for user registration and login. It supports seamless integration into **Native Android** and **Flutter** applications.

---

## ✨ Features

- ✅ Customizable UI colors and labels  
- ✅ Supports showing/hiding username input  
- ✅ Returns user data (email, password, first/last name, username)  
- ✅ Works with both Android and Flutter  
- ✅ Easy integration via method channels for Flutter

---

## 📱 Native Android Integration (Jetpack Compose)

### ✅ Requirements

- Android Studio Electric Eel or later
- Android SDK 24+
- Kotlin 1.9.0
- Compose 1.5.1
- Jetpack Compose enabled project
- Compile SDK 35

---

### 🛠 Installation

1. **Add SDK to your Android project**
   Create a libs folder under the app folder and copy the library aar file from [here](https://github.com/EQua-Dev/Assessment_Auth_SDK_Android/blob/main/authSDK-debug.aar) into the libs folder
   ``` bash
   app/libs/AuthSDK.aar
   ```
***Modify your app build.gradle file***
  Add the library to the app gradle dependencies
  ``` bash
debugImplementation(files("libs/authSDK-debug.aar"))
```

***Modify the settings.gradle file***
  Include the SDK into the project
  ``` bash
include(":authSDK")
```

2. **Set up the project for the SDK UI**
The SDK uses Jetpack Compose and so, if the project does not support Jetpack Compose, include its libraries and support in the app/build.gradle file

***Include these libraries into the dependencies block***
``` bash

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
```

***Add the support into the android block***
``` bash
  buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
```

3. **Within your Composable function, utilise the SDK**
   ***Import the library files (Android Studio could help with this automatically)***
```bash

import tech.sourceid.assessment.authsdk.AuthActivity
import tech.sourceid.assessment.authsdk.data.AuthConfig
import tech.sourceid.assessment.authsdk.data.AuthConfigHolder
```
***Call the SDK***
``` kotlin
Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        Button(onClick = {
                            AuthConfigHolder.callback = { userData ->
                                // ✅ You can use userData here
                                Log.d("MainActivity", "User logged in: $userData")
//                                Toast.makeText(this, "Welcome ${userData.firstName}", Toast.LENGTH_SHORT).show()
                            }

                            // Optional: You can set config here too
                            AuthConfigHolder.config = AuthConfig(
                                submitButtonText = "Login",
                                primaryColor = Color.Blue
                            )

                            val intent = Intent(this@MainActivity, AuthActivity::class.java)
                            startActivity(intent)
                        }) {
                            Text("Launch Auth SDK")
                        }
                    }
                }
```

5. **Use the result from the SDK as you please**
---

### 📤 Returned UserData Model

   ```kotlin
   data class UserData(
    val email: String,
    val password: String,
    val username: String?,
    val firstName: String,
    val lastName: String
)
```
---

### 📦 Native Android Demo App
👉🏽 You can view its implementation [here](https://github.com/EQua-Dev/AuthSDK_Demo_App)


## 💙 Flutter Integration

### ✅ Requirements

- Flutter 3.0+
- Kotlin support enabled in your Flutter Android project
- Method channel support for native communication

---

### 🛠 Setup Instructions

1. **Add SDK to your Flutter project**  
   - Create a libs folder under the app folder
   - Copy the library aar file from [here](https://github.com/EQua-Dev/Assessment_Auth_SDK_Android/blob/main/authSDK-debug.aar) into the libs folder
   ``` bash
   android/app/libs/AuthSDK.aar
   ```
***Modify your app build.gradle file***
  Add the library to the app gradle dependencies
  ``` bash
implementation(name: 'authSDK-debug', ext: 'aar')
```

***Modify the settings.gradle file***
  Include the SDK into the project
  ``` bash
include ":authSDK"
```

2. **Set up the project for the SDK UI**
The SDK uses Jetpack Compose and so, if the project does not support jetpack compose, include its libraries and support in the app/build.gradle file

***Include these libraries into the dependencies block***
``` bash

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
```

***Add the support into the android block***
``` bash
  buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
```

3. **Configure Android Platform Code (MainActivity.kt)**

***Import the SDK as well as Jetpack Compose libraries and the FlutterEngine***
```kotlin

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


import android.content.Intent
import tech.sourceid.assessment.authsdk.data.AuthConfig
import tech.sourceid.assessment.authsdk.data.AuthConfigHolder
import tech.sourceid.assessment.authsdk.AuthActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.core.graphics.toColorInt
```

***Modify your class function***
Create the channel variable (make sure the CHANNEL string value is the same as declared in the iOS AppDelegate file)
```kotlin
class MainActivity: FlutterActivity(){
    private val CHANNEL = "auth_sdk_channel"  //<-- Add this line at the top
```
***Setup and call the SDK class***
```kotlin
  
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "launchAuthSDK") {
                val config = call.argument<Map<String, Any>>("config")
                launchAuthScreen(config, result)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun launchAuthScreen(configMap: Map<String, Any>?, result: MethodChannel.Result) {
        // Set config values
        val config = AuthConfig(
            primaryColor = Color(configMap?.get("primaryColor").toString().toColorInt()),
            backgroundColor = Color(configMap?.get("backgroundColor").toString().toColorInt()),
            textColor = Color(configMap?.get("textColor").toString().toColorInt()),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            submitButtonText = configMap?.get("submitButtonText") as String,
            showUsername = configMap["showUsername"] as Boolean
        )
        AuthConfigHolder.config = config

        // Set the callback
        AuthConfigHolder.callback = { user ->
            val userMap = mapOf(
                "email" to user.email,
                "password" to user.password,
                "username" to user.username,
                "firstName" to user.firstName,
                "lastName" to user.lastName
            )
            result.success(userMap)
        }

        // Start the SDK screen
        startActivity(Intent(this, AuthActivity::class.java))
    }
```

***Your full MainActivity file should look like this***
```kotlin
package tech.sourceid.assessment.source_auth

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


import android.content.Intent
import tech.sourceid.assessment.authsdk.data.AuthConfig
import tech.sourceid.assessment.authsdk.data.AuthConfigHolder
import tech.sourceid.assessment.authsdk.AuthActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.core.graphics.toColorInt



class MainActivity: FlutterActivity(){

    private val CHANNEL = "auth_sdk_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            if (call.method == "launchAuthSDK") {
                val config = call.argument<Map<String, Any>>("config")
                launchAuthScreen(config, result)
            } else {
                result.notImplemented()
            }
        }
    }

    private fun launchAuthScreen(configMap: Map<String, Any>?, result: MethodChannel.Result) {
        // Set config values
        val config = AuthConfig(
            primaryColor = Color(configMap?.get("primaryColor").toString().toColorInt()),
            backgroundColor = Color(configMap?.get("backgroundColor").toString().toColorInt()),
            textColor = Color(configMap?.get("textColor").toString().toColorInt()),
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            submitButtonText = configMap?.get("submitButtonText") as String,
            showUsername = configMap["showUsername"] as Boolean
        )
        AuthConfigHolder.config = config

        // Set the callback
        AuthConfigHolder.callback = { user ->
            val userMap = mapOf(
                "email" to user.email,
                "password" to user.password,
                "username" to user.username,
                "firstName" to user.firstName,
                "lastName" to user.lastName
            )
            result.success(userMap)
        }

        // Start the SDK screen
        startActivity(Intent(this, AuthActivity::class.java))
    }
}

```
3. **In your Flutter project, create a class to call the SDK**
```dart
import 'package:flutter/services.dart';

class AuthSDK {
  static const MethodChannel _channel = MethodChannel('auth_sdk_channel');

  static Future<Map<String, dynamic>?> launchAuthScreen({
    required String primaryColor,
    required String backgroundColor,
    required String textColor,
    String submitButtonText = 'Submit',
    bool showUsername = true,
  }) async {
    try {
      final result = await _channel.invokeMethod('launchAuthSDK', {
        'config': {
          'primaryColor': primaryColor,
          'backgroundColor': backgroundColor,
          'textColor': textColor,
          'submitButtonText': submitButtonText,
          'showUsername': showUsername,
        }
      });
      return Map<String, dynamic>.from(result);
    } on PlatformException catch (e) {
      print("Auth SDK Error: ${e.message}");
      return null;
    }
  }
}

```
4. **Call the SDK class from your widget to launch the SDK**
```dart
     @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('SDK Demo')),
      body: Center(
        child: ElevatedButton(
          child: Text("Launch Auth SDK"),
          onPressed: () async {
            final userData = await AuthSDK.launchAuthScreen(
              primaryColor: "#6200EE",
              backgroundColor: "#FFFFFF",
              textColor: "#000000",
              submitButtonText: "Register New User",
              showUsername: true,
            );

            if (userData != null) {
              print("User: $userData");
              ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text("Welcome, ${userData['firstName']}!")),
              );
            }
          },
        ),
      ),
    );
  }
}

```
---

### 📦 Flutter Demo App
👉🏽 You can view its implementation [here](https://github.com/EQua-Dev/Flutter_AuthSDK_Implementation)


## 📱 iOS Version
For iOS developers, the iOS version of the SDK can be found [here](https://github.com/EQua-Dev/Assessment_Auth_SDK_iOS)
