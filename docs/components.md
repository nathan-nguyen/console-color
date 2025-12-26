# Components in Detail

## ServerMessageQueue

The idea of `ServerMessageQueue` is to have a background thread to broadcast message to all clients asynchronously.
This reduces the process time of each frame. However, enabling background thread causes several issues:
- Corrupted serialization data: When object is serialized, its attributes are updated by another thread. This causes serialized data to be corrupted.
- `ConcurrentModificationException`: While looping through collections to serialize data, if that collection is updated by another thread, it throws `ConcurrentModificationException`.
Disabled background thread, broadcast step takes average of 2ms.

## ModelManager

- Models are being processed in parallel. This causes race condition when 2 models are trying to move into the same position.
- Disable parallel processing to benchmark the performance and improvement of each step.

## HitboxManager

- Hitbox is draw using `model.posX`, `model.posY`, `hitbox.height` and `hitbox.weight`. Hitbox dimension is seprated from Model, however they share the same measurement units.
- Position (`model.posX`, `model.posY`) presents top left corner of hitbox rectangle.
