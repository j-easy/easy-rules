package org.jeasy.rules.tutorial.springboot.pojo;

/**
 * my order
 *
 * @author venus
 * @version 1
 */
public class MyOrder {

    /**
     * weather customer order or purchase order
     */
    private boolean customerOrder;

    public MyOrder(boolean customerOrder) {
        this.customerOrder = customerOrder;
    }

    public boolean isCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(boolean customerOrder) {
        this.customerOrder = customerOrder;
    }
}
