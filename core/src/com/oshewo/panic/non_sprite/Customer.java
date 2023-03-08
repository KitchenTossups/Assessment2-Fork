package com.oshewo.panic.non_sprite;

import java.util.Date;

public class Customer {

    private final Recipe order;
    private final long orderPlaced, customerNumber;

    public Customer(Recipe order, long customerNumber, long delay) {
        this.order = order;
        this.orderPlaced = new Date().getTime() + 11000 + delay;
        this.customerNumber = customerNumber;
    }

    public Recipe getOrder() {
        return this.order;
    }

    public long getOrderPlaced() {
        return this.orderPlaced;
    }

    public long getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "order=" + order.toString() +
                ", orderPlaced=" + orderPlaced +
                ", customerNumber=" + customerNumber +
                '}';
    }
}
