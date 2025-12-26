package com.noiprocs.core.common;

import java.util.LinkedList;

public class RollingWindowStatistics {
  private final int maxSize;

  private long sum;
  private final LinkedList<Long> queue = new LinkedList<>();

  public RollingWindowStatistics(int maxSize) {
    this.maxSize = maxSize;
  }

  public void add(long number) {
    queue.add(number);
    sum += number;
    while (queue.size() > maxSize) {
      sum -= queue.poll();
    }
  }

  public long getAvg() {
    if (queue.isEmpty()) return -1;
    return sum / queue.size();
  }

  public long getLast() {
    if (queue.isEmpty()) return -1;
    return queue.peekLast();
  }
}
