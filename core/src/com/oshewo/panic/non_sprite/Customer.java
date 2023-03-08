package com.oshewo.panic.non_sprite;

public class Customer {

    private final Recipe order;
    private final long orderPlaced;

    public Customer(Recipe order, long timer) {
        this.order = order;
        this.orderPlaced = timer;
    }

    public Recipe getOrder() {
        return this.order;
    }

    public long getOrderPlaced() {
        return this.orderPlaced;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "order=" + order.toString() +
                ", orderPlaced=" + orderPlaced +
                '}';
    }
}
