package com.noiprocs.ui.console.sprite;

import com.noiprocs.core.graphics.RenderableSprite;
import com.noiprocs.core.model.Model;

public class ConsoleSprite extends RenderableSprite {
  protected static final int OFFSET_X = 0, OFFSET_Y = 0;
  protected static final char[][] EMPTY_TEXTURE = new char[0][0];

  public int offsetX, offsetY;
  private char[][] texture;

  public ConsoleSprite(char[][] texture) {
    this(texture, OFFSET_X, OFFSET_Y);
  }

  public ConsoleSprite(char[][] texture, int offsetX, int offsetY) {
    this.texture = texture;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
  }

  protected void setTexture(char[][] texture) {
    this.texture = texture;
  }

  public char[][] getTexture(Model model) {
    return texture;
  }

  public void render() {}
}
