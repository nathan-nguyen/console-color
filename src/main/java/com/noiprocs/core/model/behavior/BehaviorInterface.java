package com.noiprocs.core.model.behavior;

import com.noiprocs.core.model.Model;
import java.io.Serializable;

public interface BehaviorInterface extends Serializable {
  void update(Model model);
}
