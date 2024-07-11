# NYC Schools Android Client POC

## Building and Deploying
First connect a testing device or start an emulator that has a minimum Android OS SDK version of at
least 24

After connecting your test device you can deploy this app from inside of Android Studio by building
and deploying the 'app' module on your connected device. If you are not using Android Studio you
can build and deploy the apps using the following gradle command (this command assumes only
one device is connected to the host machine), assuming the android build tools are installed on 
your system:
```shell
  $ ./gradlew installDebug
```

## Additional Feature
The requirements made note that the app should be useful to students, so in addition to the list
of schools and details of a single school UX there are filters build into the main screen to filter
schools out based on minimum average SAT scores.

## Testing
There are three different test modules in this app.

One of the modules is for testing the DataSource
implementation. 

Another test module is an actual integration test with the remote database, which
checks that the connection actually works and delivers at least some data to our app in a way that
the app is able to understand. Note about this test: If the actual requests fails then this test
will also fail; this test requires the server to be reachable and responsive.

To test both of the unit tests mentioned above you can run this command:
```shell
  $ ./gradlew test
```

The last test module is for the MainScreen Compose UI module. This test is to make sure the UI
reacts correctly for loading and showing data to the user.

This last test requires a connected test device that run the app to verify the results. After
connecting a test device you can run the last test like using this command:
```shell
  $ ./gradlew connectedAndroidTest
```

## Dependency Injection
One of the test criteria mentions showcasing Dependency Injection; you can see an example of
"Manual Dependency Injection" (ref: 
[Android Developer docs](https://developer.android.com/training/dependency-injection/manual))
in the NycSchoolsViewModel class as noted in the referenced documentation.
