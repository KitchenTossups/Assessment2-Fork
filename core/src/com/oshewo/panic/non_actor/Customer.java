package com.oshewo.panic.non_actor;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.*;
import java.util.concurrent.atomic.*;

public class Customer {         // Sets out how customers will behave and what happens when they leave

    private final Recipe order;
    private final long orderPlaced;
    private boolean penalty = false;

    public Customer(Recipe order) {
        this.order = order;
        this.orderPlaced = new Date().getTime();
    }

    public Recipe getOrder() {
        return this.order;
    }

    public long getOrderPlaced() {
        return this.orderPlaced;
    }

    public boolean isPenalty() {
        return this.penalty;
    }

    public void setPenalty() {
        this.penalty = true;
    }

    public String getSaveConfig(Map<Long, Long> timesInPause) {
        long time = TimeUtils.timeSinceMillis(this.orderPlaced);
        AtomicReference<AtomicLong> atomicTime = new AtomicReference<>(new AtomicLong(time));
        timesInPause.forEach((key, value) -> {
            if (this.orderPlaced < key) {
                atomicTime.updateAndGet((v) -> new AtomicLong(v.get() - value));
            }
        });
        return String.format("%s~%d~%b", this.order.getEndProduct().getString(), atomicTime.get().get(), this.penalty);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "order=" + this.order.toString() +
                ", orderPlaced=" + this.orderPlaced +
                '}';
    }
}
