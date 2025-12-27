# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A multiplayer console-based game with client-server architecture built in Java using Maven. The game features a real-time multiplayer environment with collision detection, sprites, and network synchronization.

## Architecture

The game uses a component-based architecture centered around `GameContext`, which coordinates all major systems:

### Core Components

- **GameContext**: Central singleton managing the game lifecycle and component coordination
  - `TimeManager`: Fixed timestep game loop (20 ticks/second, 60 FPS)
  - `ControlManager`: Processes player input and sends commands
  - `NetworkManager`: Handles client-server communication using Netty
  - `ModelManager`: Manages all game entities and their behavior
  - `HitboxManager`: Collision detection and validation
  - `SpriteManager`: Visual representation mapping
  - `GameScreen`: Rendering interface (console-based)

### Key Design Patterns

**Client-Server Architecture**: The server is authoritative and broadcasts state to clients every 3 frames (`BROADCAST_DELAY`). Clients send commands to the server, which validates and processes them.

**Chunk-Based Processing**: `ModelManager` divides the world into chunks (`ModelChunk`) to optimize processing. Only models in active chunks are updated each frame.

**Interface-Based Graphics**: The UI layer is abstracted through interfaces (`GameScreenInterface`, `HitboxManagerInterface`, `RenderableSprite`), allowing the console implementation to be swapped for other rendering systems.

**Kryo Serialization**: Uses Kryo for efficient network serialization instead of Java's built-in serializer to avoid corruption during concurrent updates.

### Model Hierarchy

```
Model (abstract base)
├── MobModel (entities with movement/AI)
│   ├── CotMobModel (game-specific mobs)
│   ├── PlayerModel (player characters)
│   └── ProjectileModel (flying objects)
├── TreeModel (plants/obstacles)
│   ├── PineTreeModel
│   └── BirchTreeModel
├── ItemModel (pickable items)
└── Environment models (walls, boundaries, etc.)
```

### Critical Implementation Details

**Collision System**: Position (`model.position`) is a Vector3D representing the top-left corner of the hitbox rectangle. Access coordinates via `model.position.x` (row, increases top to bottom) and `model.position.y` (column, increases left to right). The bottom-right corner is calculated as `model.position.add(hitbox.getDimension())`. Collision detection uses category-based masks defined in `hitbox-config.json`.

**Parallel Processing**: `ModelManager` has experimental parallel processing using `parallelStream()`, but it's noted to cause race conditions when models move to the same position. Review performance before enabling.

**Background Broadcasting**: `ServerMessageQueue` has a disabled background thread option (`USE_BROADCAST_BG_THREAD = false`) due to serialization corruption and `ConcurrentModificationException` issues. The synchronous broadcast takes ~2ms.

## Configuration Files

### Model Configuration (`src/main/resources/`)

**hitbox-config.json**: Defines collision boxes for models
- Fields: `modelClass`, `height`, `width`, `category`, `mask`
- Categories: `WALL`, `PLAYER`, `MOB`, `ITEM`, `PROJECTILE`
- Supports Config constant references (e.g., `"Config.MAZE_WALL_THICKNESS_HEIGHT"`)

**sprite-config.json**: Defines visual appearance
- Fields: `modelClass`, `texture`, `offsetX`, `offsetY`
- Texture uses ASCII art, `~` is transparent
- Example: `["(=)", "~|~", "~|~"]` for an axe sprite

See `docs/model-configuration.md` for detailed examples.

### Game Constants (`Config.java`)

Key configurable values:
- `MAX_FPS = 60`, `GAME_TICKS_PER_SECOND = 20`
- `BROADCAST_DELAY = 3` (frames between server broadcasts)
- `AUTO_SAVE_DURATION = 1800` (frames between auto-saves)
- `USE_KRYO_SERIALIZATION = true`
- `USE_BROADCAST_BG_THREAD = false` (keep disabled)

## Testing

Test files are in `src/test/java/com/noiprocs/`. Key test areas:
- Hitbox configuration validation (`HitboxConfigVerificationTest`)
- Collision detection (`ConsoleHitboxManagerTest`)
- Hitbox factory behavior (`ConsoleHitboxFactoryTest`)

Run single test class:
```bash
mvn test -Dtest=ClassName
mvn test -Dtest=ConsoleHitboxManagerTest
```

## Key Implementation Notes

**Adding New Models**: Requires three steps:
1. Create model class in appropriate package
2. Add hitbox entry to `hitbox-config.json`
3. Add sprite entry to `sprite-config.json`

**Network Architecture**: Uses `network-client-server` in-house dependency (version 1.0-SNAPSHOT). Server broadcasts full state periodically; clients are stateless renderers.

**Performance Benchmarking**: To benchmark, delete `last_checkpoint.dat`, increase `ModelChunk.CHUNK_HEIGHT` and `CHUNK_WIDTH` to force single-chunk processing, start server + 1 client, and observe `MetricCollector` output (enable with `DEBUG_MODE = true`).

**Save/Load System**: Game state is saved to `last_checkpoint.dat` every `AUTO_SAVE_DURATION` frames. The `SaveLoadManager` handles serialization.

## Package Structure

- `com.noiprocs.core.*`: Core game logic (models, managers, network)
- `com.noiprocs.ui.console.*`: Console-based UI implementation
  - `sprite/`: ASCII sprite implementations
  - `hitbox/`: Collision detection implementation
- `com.noiprocs.core.model.*`: Entity definitions
  - `mob/`: Moving entities (players, enemies, projectiles)
  - `item/`: Inventory items
  - `plant/`: Trees and vegetation
  - `environment/`: Walls, boundaries, maze parts
  - `building/`: Placeable structures

## Documentation

Additional documentation in `docs/`:
- `architecture.md`: Component interaction diagram
- `components.md`: Implementation details and known issues
- `model-configuration.md`: Detailed guide for hitbox/sprite config
- `development.md`: Benchmarking guide and improvement roadmap
