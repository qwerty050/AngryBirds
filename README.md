# Angry Birds-Inspired Game

## Overview

This project is an Angry Birds-style game developed for the Advanced Programming course, utilizing Java and LibGDX. The game combines engaging gameplay mechanics with technical implementation to showcase advanced programming concepts.

## Prerequisites

- Java Development Kit (JDK)
- IntelliJ IDEA
- Gradle

## Project Setup

### Using IntelliJ IDEA

1. Open IntelliJ IDEA
2. Open the `build.gradle` file
   - Select "Open as Project"
3. Wait for Gradle sync to complete

### Running the Game

#### Method 1: Using Gradle Tasks
1. Extend the Gradle tab on the right side of your window
2. Navigate to: `lwjgl3` -> `Tasks` -> `application` -> `run`

#### Method 2: Run Configuration
1. Right-click on the `Lwjgl3Launcher` class
2. Select 'Run Lwjgl3Launcher.main()'

### Command Line Execution

#### Desktop Run
To run the project via command line:

```bash
# First, make gradlew executable (if needed)
chmod +x gradlew

# Run the game
./gradlew lwjgl3:run
```

## Development Environment

- **Language**: Java
- **Framework**: LibGDX
- **Build Tool**: Gradle

## Project Structure

- `core/`: Contains the main game logic and core implementation
- `lwjgl3/`: Contains platform-specific desktop launch configuration
- `assets/`: Stores game resources like sprites, sounds, and textures



## Contributions

2023050 - Aditya Singh - Game Logic Design
2023048 - Aditya Raj Yadav - Level Design

Project Link: https://github.com/qwerty050/AngryBirds: