Angry Birds-Inspired Game
Overview
This game is developed for the Advanced Programming course using Java and LibGDX.

How to Execute
Using IntelliJ IDE
Open the build.gradle file.
Select "Open as Project" to get started.
Extend the Gradle tab on the right side of your window.
Expand the tasks of your project, then select:

lwjgl3 -> Tasks -> application -> run

Alternatively, you can create a run configuration:

Right-click your Lwjgl3Launcher class.
Select ‘Run Lwjgl3Launcher.main()’.


Command Line
All targets can be run and deployed via the command line interface. To run your project on the desktop, use the following command:

./gradlew lwjgl3:run

Note: You may need to set gradlew as executable using the following command:

chmod +x gradlew
