package com.noiprocs.core.util;

import java.util.LinkedList;
import java.util.Queue;

public class RollingWindowStatistics {
    private final int maxSize;

    private int sum;
    private final Queue<Long> queue = new LinkedList<>();

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
        if (queue.isEmpty())
            return -1;
        return sum / queue.size();
    }
}
