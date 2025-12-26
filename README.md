# console-color

A multiplayer console-based game with client-server architecture.

## Quick Start

### Commands

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

### Controls

- A D W S : Movement
- H: Stop
- F: Trigger action
- T: Use current item
- 1 2 3 4: Switch item in inventory

## Documentation

- [Architecture](docs/architecture.md) - System architecture and component interaction
- [Components](docs/components.md) - Detailed component implementation notes
- [Model Configuration](docs/model-configuration.md) - Guide for configuring hitboxes and sprites for new models
- [Development](docs/development.md) - Benchmarking guide and improvement roadmap