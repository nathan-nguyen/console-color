# Development Guide

## How to benchmark performance

1. Delete save file `last_checkpoint.dat`.
2. Increase `ModelChunk.CHUNK_HEIGHT` and `ModelChunk.CHUNK_WIDTH` to force all models to be processed (by being in 1 chunk).
3. Start server and start 1 client.
4. Record the measurement.

## Improvements

### Future improvements

- Fix bugs and remove try / catch or move try / catch in proper positions.
- Improve HitboxManager - isValid method with moving vector.
    - At the moment, we loop and check whether each step is valid.
    - The better way is to check for the moving vector and get the position which model could move to.
- Make ModelManager abstract, separate into ClientModelManager and ServerModelManager.
- Review the use of `parallelStream`, measure the performance.

### Past Improvements

- Divided ModelManager into chunks.
- Broadcast data to client asynchronously.
- Used better serializer (i.e Kryo, ...). Issue with Java serializer: When object's attributes are updated while object is serialized, this caused serialized data to be corrupted.
- Used `netty` for communication via network.
- Used text files for `ConsoleHitboxFactory` instead of hardcoding.
- Used text files for `ConsoleSpriteFactory` instead of hardcoding.
