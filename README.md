# EzLib

A helper library for **client-side QoL Fabric mods** targeting Minecraft 1.26.x.

---

## API Overview

| Class | Purpose |
|-------|---------|
| `EzClient` | Minecraft / player / world access |
| `EzLogger` | Prefixed SLF4J logging |
| `EzColor` | ARGB colour constants & factory |
| `EzOutline` | Outline colour constants |
| `EzText` | Fluent HUD/GUI text renderer |
| `EzButton` | Fluent button + toggle builder |
| `EzGui` | Fluent GUI screen builder |
| `EzHUD` | HudElementRegistry registration |
| `EzConfig` | JSON config save/load (Gson) |
| `EzInventory` | Inventory item queries |
| `EzItem` | ItemStack helpers |
| `EzPlayer` | Movement state + action builder |
| `EzWorld` | Day/night, weather, biome |
| `EzKeybind` | Key binding registration |
| `EzToast` | System toast messages |
| `EzSound` | Sound event player |
| `EzParticle` | Particle emitter builder |
| `EzNotification` | On-screen overlay notifications |
| `EzMath` | clamp / lerp / distance |
| `ConfigScreenFactory` | Quick config screen from toggles |

---

[![](https://jitpack.io/v/ItsRaphaelakos/EzLib.svg)](https://jitpack.io/#ItsRaphaelakos/EzLib)

## 1 — Building EzLib

### Requirements

- **Java 25** (set `JAVA_HOME` to your JDK 25 installation)
- **Gradle 9.4** (provided via the wrapper)

### Steps

```bash
# Clone the repo
git clone https://github.com/ItsRaphaelakosYT/EzLib.git
cd EzLib

# Build (produces the JAR in build/libs/)
./gradlew build          # Linux / macOS
gradlew.bat build        # Windows
```

The output JAR will be at:

```
build/libs/ezlib-1.0.0.jar
```

---

## 2 — Using EzLib in another mod (local libs)

1. Copy `ezlib-1.0.0.jar` into a `libs/` folder in your mod project.

2. In your mod's `build.gradle`, add:

```groovy
repositories {
    flatDir { dirs 'libs' }
}

dependencies {
    implementation files('libs/ezlib-1.0.0.jar')
}
```

3. In `fabric.mod.json`, declare the dependency:

```json
"depends": {
    "ezlib": ">=1.0.0"
}
```

4. Sync/refresh your Gradle project in your IDE. EzLib classes will be on the classpath.

---

## 3 — Using EzLib via JitPack (once published on GitHub)

Once the repository is pushed to GitHub as `ItsRaphaelakosYT/EzLib`, anyone can depend on it
through [JitPack](https://jitpack.io) without hosting a Maven server.

In your mod's `build.gradle`:

```groovy
repositories {
    maven {
        name = "JitPack"
        url = "https://jitpack.io"
    }
}

dependencies {
    implementation "com.github.ItsRaphaelakosYT:EzLib:1.0.0"
}
```

> **Note:** Replace `1.0.0` with any Git tag, branch name, or commit hash.
> JitPack builds the project on first request; subsequent requests are served from cache.

---

## License

MIT © ItsRaphaelakosYT
