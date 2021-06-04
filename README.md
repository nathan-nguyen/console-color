# console-color

## Architecture

```
|- GameScreen [package graphics]
    |- 
|- model: Game models
|   |- ModelManager
|   |   |_ ServerModelManager: Contain all models
|   |       |_ Model
|_ util: Will be decided later
    |- SaveLoadManager: Save/Load ServerModelManager
```

## Commands

```bash
# Server
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc gnik server localhost 8080"

# Client noiprocs
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc noiprocs client localhost 8080"

# Client yaiba
mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc yaiba client localhost 8080"
```

## Future improvement
- Divide ModelManager into chunks.
