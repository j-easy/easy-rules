/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package net.benas.easyrules.samples.order;

import net.benas.easyrules.core.Rule;

/**
 * Business rule class that defines suspect order rule.
 *
 * @author benas (md.benhassine@gmail.com)
 */
class SuspectOrderRule extends Rule implements SuspectOrderJmxManagedRule {

    private float suspectOrderAmountThreshold = 1000;

    private Order order;

    private Customer customer;

    SuspectOrderRule(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        return order.getAmount() > suspectOrderAmountThreshold && customer.isNew();
    }

    @Override
    public void performActions() throws Exception {
        System.out.println("Alert : A new customer [id=" + customer.getCustomerId() + "] has checked out an order [id=" +
                order.getOrderId() + "] with amount " + order.getAmount() + " > " + suspectOrderAmountThreshold);
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getSuspectOrderAmountThreshold() {
        return suspectOrderAmountThreshold;
    }

    public void setSuspectOrderAmountThreshold(float suspectOrderAmountThreshold) {
        this.suspectOrderAmountThreshold = suspectOrderAmountThreshold;
    }

}
