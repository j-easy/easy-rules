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

import net.benas.easyrules.api.RulesEngine;
import net.benas.easyrules.core.DefaultRulesEngine;

/**
 * Launcher class of the order sample.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class OrderSampleLauncher {

    public static void main(String[] args) {

        Order order = new Order(6654, 1200);
        Customer customer = new Customer(2356, true);

        /**
         * Create a business rule instance
         */
        SuspectOrderRule suspectOrderRule = new SuspectOrderRule(
                "Suspect Order",
                "Send alert if a new customer checks out an order with amount greater than 1000$",
                1);

        /**
         * Set data to operates on
         */
        suspectOrderRule.setOrder(order);
        suspectOrderRule.setCustomer(customer);

        /**
         * Create a default rules engine and register the business rule
         */
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRule(suspectOrderRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}
