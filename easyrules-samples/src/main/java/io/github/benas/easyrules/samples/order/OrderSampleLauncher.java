/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package io.github.benas.easyrules.samples.order;

import io.github.benas.easyrules.api.RulesEngine;
import io.github.benas.easyrules.core.DefaultRulesEngine;

/**
 * Launcher class of the order sample.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
public class OrderSampleLauncher {

    public static void main(String[] args) throws InterruptedException {

        Order order = new Order(6654, 1200);
        Customer customer = new Customer(2356, true);

        /**
         * Create a business rule instance
         */
        SuspectOrderRule suspectOrderRule = new SuspectOrderRule(
                "Suspect Order",
                "Send alert if a new customer places an order with amount greater than a threshold",
                1);

        /**
         * Set data to operate on
         */
        suspectOrderRule.setOrder(order);
        suspectOrderRule.setCustomer(customer);

        /**
         * Create a default rules engine and register the business rule
         */
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerJmxManagedRule(suspectOrderRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

        // Suspend execution for 30s to have time to update suspect order amount threshold via a JMX client.
        Thread.sleep(300000);

        System.out.println("**************************************************************");
        System.out.println("Re fire rules after updating suspect order amount threshold...");
        System.out.println("**************************************************************");

        rulesEngine.fireRules();

    }
}
