---
description: How to install the StudyNest app on an Android device for testing
---

# How to Install StudyNest on Your Mobile Device

You have two main options to test the app on your physical Android device:

## Option 1: Connect via USB (Recommended)
This allows you to run the app directly and see logs (Logcat) for debugging.

1.  **Enable Developer Options on your phone**:
    *   Go to **Settings** > **About Phone**.
    *   Tap **Build Number** 7 times until you see "You are now a developer!".
2.  **Enable USB Debugging**:
    *   Go to **Settings** > **System** > **Developer Options**.
    *   Enable **USB Debugging**.
3.  **Connect your phone** to your Mac using a USB cable.
4.  **Trust the computer**: If prompted on your phone, tap "Allow" or "Trust".
5.  **Run the App**:
    *   Open the project in Android Studio.
    *   Select your connected device from the device dropdown in the toolbar.
    *   Click the green **Run** button (Play icon).

## Option 2: Build and Transfer APK
If you want to send the app to someone else or install it without a cable connection (after transferring).

1.  **Build the APK**:
    *   Open the terminal in your project root.
    *   Run the following command:
    ```bash
    ./gradlew assembleDebug
    ```
2.  **Locate the APK**:
    *   Once the build finishes, the APK will be located at:
        `app/build/outputs/apk/debug/app-debug.apk`
3.  **Transfer and Install**:
    *   Transfer this `app-debug.apk` file to your phone (via USB, Google Drive, WhatsApp, etc.).
    *   Open the file on your phone.
    *   You may need to allow "Install from unknown sources" for the app you are opening it from (e.g., Files app or Browser).
    *   Tap **Install**.

## Troubleshooting
*   **Device not found?** Check your USB cable and ensure USB Debugging is on. Try running `adb devices` in the terminal to see if your computer detects the phone.
*   **Installation Failed?** If you have an older version of the app installed, try uninstalling it first.
