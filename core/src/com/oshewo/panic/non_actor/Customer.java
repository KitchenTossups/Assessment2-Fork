package com.oshewo.panic.non_actor;

import java.util.Date;

public class Customer {

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

    @Override
    public String toString() {
        return "Customer{" +
                "order=" + this.order.toString() +
                ", orderPlaced=" + this.orderPlaced +
                '}';
    }
}
