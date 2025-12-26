package com.noiprocs.core.model.item;

import com.noiprocs.core.model.Model;

public class AppleItem extends Item {
  private static final String ITEM_NAME = "Apple";

  public AppleItem(int amount) {
    super(ITEM_NAME, amount);
  }

  @Override
  public void use(Model model) {}
}
