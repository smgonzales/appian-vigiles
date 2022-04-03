# Appian Vigiles #

Ancient Roman Firefighters dispatcher and firefighter implementation.  Implementation is done in server side Kotlin.

### Requirements ###

* The Java JDK should be installed in your environment.
* Project is self-contained and is able to download gradle and any needed dependencies.

### Building ###

* Navigate to the root of the project using a command window.
* Execute "./gradlew build" if on MacOS otherwise for Windows execute "gradlew.bat build"
* The build gradle task will conduct the build and perform the associated BasicScenario tests.

### Test Report ###

Execute "./gradlew viewTestReport" if on MacOS to see results of tests execution. If on Windows then open the file at "build/reports/tests/test/index.html" in a web browser.

### IntelliJ IDE ###

Coding was done using IntelliJ with Gradle integration.  IntelliJ will be able to import and configure the project appropriately based on the gradle build files present in the project.  To allow IntelliJ to autoconfigure either create it as a new project from version control or import it as a Gradle project from existing sources. 

### References ###

* https://kotlinlang.org/docs/getting-started.html
* https://kotlinlang.org/lp/server-side
