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

package org.easyrules.samples.order;

import org.easyrules.core.DefaultRulesEngine;

import java.util.Scanner;

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
                "Send alert if a new customer places an order with amount greater than a threshold");

        /**
         * Set data to operate on
         */
        suspectOrderRule.setOrder(order);
        suspectOrderRule.setCustomer(customer);

        /**
         * Create a default rules engine and register the business rule
         */
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerJmxRule(suspectOrderRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

        // Update suspect order amount threshold via a JMX client.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Change suspect order amount threshold to a value > 1200 via a JMX client and then press enter");
        scanner.nextLine();

        System.out.println("**************************************************************");
        System.out.println("Re fire rules after updating suspect order amount threshold...");
        System.out.println("**************************************************************");

        rulesEngine.fireRules();

    }
}
