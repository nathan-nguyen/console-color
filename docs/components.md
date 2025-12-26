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

- Hitbox is drawn using `model.position` and `hitbox.getDimension(model)`. Hitbox dimension is separated from Model, however they share the same measurement units.
- Position (`model.position`) is a Vector3D representing the top-left corner of hitbox rectangle. Access coordinates via `model.position.x` (row) and `model.position.y` (column).
- Coordinate system: X increases from top to bottom, Y increases from left to right.
- Hitbox dimensions are accessed via `hitbox.getDimension(model)` which returns a Vector3D where `dimension.x` = height and `dimension.y` = width.
