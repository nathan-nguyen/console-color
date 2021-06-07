# console-color

## Architecture

### Overall architecture
```
GameContext
- TimeManager
- ControlManager
- NetworkManager
- ModelManager
- SpriteManager
- HitboxManager
- GameScreen
```

### Interaction graph
```mermaid
graph LR
  subgraph GameContext
    subgraph model
      ModelManager
    end
    subgraph network
      NetworkManager
    end
    subgraph graphics
      SpriteManager
      HitboxManager
      GameScreen
    end
    ControlManager -.-> NetworkManager;
    ControlManager -.-> ModelManager;
    NetworkManager -.-> ControlManager;
    NetworkManager -.-> ModelManager;
    SpriteManager -.-> ModelManager;
    HitboxManager -.-> SpriteManager;
    GameScreen -.-> SpriteManager;
    GameScreen -.-> ModelManager;
  end
```

## Commands

```bash
# Server
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc gnik server localhost 8080"

# Client noiprocs
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc noiprocs client localhost 8080"

# Client yaiba
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc yaiba client localhost 8080"

# Assembly
mvn assembly:single
```

## Future improvement
- Divide ModelManager into chunks.
