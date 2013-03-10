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

import net.benas.easyrules.api.ConditionTrigger;

/**
 * A condition trigger that checks if a new customer checks out an order having an amount greater than the threshold.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class SuspectOrderConditionTrigger implements ConditionTrigger {

    private Order order;

    private Customer customer;

    public SuspectOrderConditionTrigger(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
    }

    @Override
    public boolean triggerCondition() {
        return order.getAmount() > Order.ORDER_AMOUNT_THRESHOLD && customer.isNew();
    }

}
