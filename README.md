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

## Future improvement
- Divide ModelManager into chunk
