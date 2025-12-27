package com.noiprocs.core.model.action;

import com.noiprocs.core.model.mob.MobModel;
import java.io.Serializable;

public abstract class Action implements Serializable {
  public int actionFrameCountDown;
  private int triggerFrame;

  public Action(int actionFrameCountDown, int triggerFrame) {
    this.actionFrameCountDown = actionFrameCountDown;
    this.triggerFrame = triggerFrame;
  }

  public void update(MobModel model) {
    --actionFrameCountDown;

    if (actionFrameCountDown == triggerFrame) {
      this.interactAction(model);
    }
  }

  abstract void interactAction(MobModel model);

  public boolean isCompleted() {
    return actionFrameCountDown <= 0;
  }
}
