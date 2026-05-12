# EzLib

EzLib is an easy helper API/library mod for Ez client-side QoL Fabric mods.

## Versions

- Minecraft: 26.1.2
- Fabric Loader: 0.19.2
- Fabric API: 0.148.0+26.1.2
- Java: 25
- Gradle: 9.4.0
- Fabric Loom: 1.15.5

## Build

On Windows, make sure this CMD is using Java 25:

```bat
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-25.0.3.9-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%
java -version
```

Then:

```bat
.\gradlew.bat build
```

The jar will be in `build/libs/`.

## Use in another mod with local libs

Put the EzLib jar in your mod's `libs` folder and add:

```gradle
dependencies {
    implementation files("libs/ezlib-1.0.0.jar")
}
```

Also add to `fabric.mod.json`:

```json
"depends": {
  "ezlib": ">=1.0.0"
}
```

## JitPack idea

```gradle
repositories {
    maven {
        name = "JitPack"
        url = "https://jitpack.io"
    }
}

dependencies {
    implementation "com.github.ItsRaphaelakosYT:EzLib:1.0.4"
}
```

## Note

Some helpers are currently compile-safe placeholders for Minecraft 26.1.2 APIs that changed heavily, especially HUD/rendering/keybind helpers. Their public API names are preserved so we can improve the internals later.
