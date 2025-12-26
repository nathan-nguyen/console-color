# Model Configuration Guide

This guide explains how to configure hitboxes and sprites for new models in the game.

## Hitbox Configuration

Hitboxes are configured in `src/main/resources/hitbox-config.json`. Each hitbox defines collision detection properties for a model.

### Configuration Structure

```json
{
  "hitboxes": [
    {
      "modelClass": "com.noiprocs.core.model.ClassName",
      "height": 1,
      "width": 4,
      "category": "CATEGORY_NAME",
      "mask": ["CATEGORY1", "CATEGORY2"]
    }
  ]
}
```

### Fields

- **modelClass** (string): Fully qualified class name of the model
  - Use `modelClass` for a single model class
  - Use `modelClasses` (array) to apply the same hitbox to multiple model classes

- **height** (number or string): Height of the hitbox rectangle
  - Can be a number (e.g., `1`, `40`)
  - Can be a string reference to a config constant (e.g., `"Config.MAZE_WALL_THICKNESS_HEIGHT"`)

- **width** (number or string): Width of the hitbox rectangle
  - Can be a number (e.g., `2`, `60`)
  - Can be a string reference to a config constant (e.g., `"Config.MAZE_WALL_THICKNESS_WIDTH"`)

- **category** (string): The collision category this model belongs to
  - Available categories: `WALL`, `PLAYER`, `MOB`, `ITEM`, `PROJECTILE`

- **mask** (array of strings): Categories this model can collide with
  - Use `["WALL"]` to collide only with walls
  - Use `["WALL", "PLAYER", "MOB"]` to collide with multiple categories
  - Use `["MASK_ALL"]` to collide with everything

### Examples

#### Single Model Class
```json
{
  "modelClass": "com.noiprocs.core.model.mob.CotPsychoModel",
  "height": 1,
  "width": 4,
  "category": "MOB",
  "mask": ["WALL", "PLAYER", "MOB"]
}
```

#### Multiple Model Classes (Same Hitbox)
```json
{
  "modelClasses": [
    "com.noiprocs.core.model.plant.PineTreeModel",
    "com.noiprocs.core.model.plant.BirchTreeModel"
  ],
  "height": 1,
  "width": 2,
  "category": "WALL",
  "mask": ["WALL"]
}
```

#### Using Config Constants
```json
{
  "modelClass": "com.noiprocs.core.model.environment.MazePartModel",
  "height": "Config.MAZE_WALL_THICKNESS_HEIGHT",
  "width": "Config.MAZE_WALL_THICKNESS_WIDTH",
  "category": "WALL",
  "mask": ["WALL", "PLAYER", "MOB", "PROJECTILE"]
}
```

### Position Reference

- Position (`model.posX`, `model.posY`) represents the **top-left corner** of the hitbox rectangle
- Hitbox dimensions use the same measurement units as model positions
- The hitbox is drawn using `model.posX`, `model.posY`, `hitbox.height`, and `hitbox.width`

## Sprite Configuration

Sprites are configured in `src/main/resources/sprite-config.json`. Each sprite defines the visual representation of a model.

### Configuration Structure

```json
{
  "sprites": [
    {
      "modelClass": "com.noiprocs.core.model.ClassName",
      "texture": ["line1", "line2"],
      "offsetX": 0,
      "offsetY": 0
    }
  ]
}
```

### Fields

- **modelClass** (string): Fully qualified class name of the model

- **texture** (array of strings): Visual representation as lines of text
  - Each string is one row of the sprite
  - Use ASCII characters to draw the sprite
  - The array length determines the sprite height
  - **Special character:** `~` (tilde) is treated as a null/transparent character and will not be rendered

- **offsetX** (number, optional): Horizontal offset for rendering
  - Default: `0`
  - Shifts the sprite right by the specified amount

- **offsetY** (number, optional): Vertical offset for rendering
  - Default: `0`
  - Shifts the sprite down by the specified amount

### Examples

#### Simple 1x1 Sprite
```json
{
  "modelClass": "com.noiprocs.core.model.item.WoodLogItem",
  "texture": ["="]
}
```

#### Multi-line Sprite
```json
{
  "modelClass": "com.noiprocs.core.model.building.FenceModel",
  "texture": ["##", "##"]
}
```

#### Sprite with Offsets and Transparent Characters
```json
{
  "modelClass": "com.noiprocs.core.model.item.AxeItem",
  "texture": ["(=)", "~|~", "~|~"],
  "offsetX": 2,
  "offsetY": 1
}
```

This creates a 3-line axe sprite:
- The `~` characters create transparency, so only `(=)` and `|` are visible
- The sprite is rendered 2 units right and 1 unit down from the model's position
- Visual result:
  ```
  (=)
   |
   |
  ```

## Adding a New Model

When creating a new model class, follow these steps:

1. **Create the Model Class**
   - Implement your model in the appropriate package
   - Example: `com.noiprocs.core.model.item.NewItem`

2. **Add Hitbox Configuration**
   - Open `src/main/resources/hitbox-config.json`
   - Add a new entry to the `hitboxes` array
   - Define the dimensions, category, and collision mask

3. **Add Sprite Configuration**
   - Open `src/main/resources/sprite-config.json`
   - Add a new entry to the `sprites` array
   - Design the visual appearance using ASCII characters
   - Add offsets if needed for proper alignment

4. **Test the Configuration**
   - Start the server and client
   - Verify the sprite renders correctly
   - Test collision detection with different categories

## Tips

- Keep sprite dimensions consistent with hitbox dimensions for visual clarity
- Use offsets to center sprites that don't naturally align with their hitbox
- Use `~` (tilde) characters to create transparent/empty spaces in your sprites
- Group similar models together in the config files for better organization
- Test collision masks thoroughly to avoid unintended interactions
- Use meaningful ASCII characters that represent the object visually
