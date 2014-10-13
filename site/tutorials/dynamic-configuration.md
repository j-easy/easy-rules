---
layout: docs
title: Dynamic configuration
header: Online shop tutorial
prev_section: tutorials/hello-world
next_section: get-involved/release-notes
doc: true
---

In this tutorial, we have an online shop application and we would like to implement the following requirements:

1. Whenever a new customer places an order with an amount greater than a defined threshold, send an alert about this suspect order to the monitoring team.
2. Moreover, the order amount threshold should be reconfigurable via JMX so we can change it at runtime.

In the first part of this tutorial, we will see how to use Easy Rules to implement the business rule described in requirement #1.

In the second part, we will add JMX capability to the business rule developed in part 1 to be able to change the order amount threshold at runtime (requirement #2).

## Part 1 : Implementing the business rule

In this application, orders and customers are represented by the _Order_ and _Customer_ classes.

First, let's implement the rule's logic by extending the `BasicRule` class:

```java
public class SuspectOrderRule extends BasicRule {

    private float suspectOrderAmountThreshold = 1000;

    private Order order;

    private Customer customer;

    SuspectOrderRule(String name, String description) {
        super(name, description);
    }

    @Override
    public boolean evaluateConditions() {
        return order.getAmount() > suspectOrderAmountThreshold
                && customer.isNew();
    }

    @Override
    public void performActions() throws Exception {
        System.out.printf("Alert : A new customer [id=%s] has placed an order [id=%s] with amount %f > %f\n",
                customer.getCustomerId(), order.getOrderId(), order.getAmount(), suspectOrderAmountThreshold);
    }

    // getters and setters for customer and order fields omitted

}
```

This rule operates on an order and a customer instances which represent the business data to operate on.

The `evaluateConditions` method evaluates to true when the customer is new and the order amount is greater than the defined threshold.

The `performActions` method simply writes to the console the specified alert (this could be sending an email or another action in a real use case).

Then, let's create an Easy Rules engine and register the `SuspectOrderRule` rule:

```java
public class OrderSampleLauncher {

    public static void main(String[] args) {

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
        rulesEngine.registerRule(suspectOrderRule);

        /**
         * Fire rules
         */
        rulesEngine.fireRules();

    }
}
```

To run this tutorial, you can follow these instructions from the root directory of Easy Rules :

{% highlight bash %}
$ mvn install
$ cd easyrules-samples
$ mvn exec:java -P runOnlineShopTutorial
{% endhighlight %}

When you run this tutorial, the rule named _Suspect Order_ that we registered will be applied since the order amount (1200) is greater than the threshold (1000) and the customer 2356 is a new customer.

## Part 2 : Changing the order amount threshold at runtime

In this tutorial, we need to expose the order amount threshold as a JMX attribute. So first, let's define an interface that allows us to change order amount threshold via JMX:

```java
@javax.management.MXBean
public interface SuspectOrderJmxRule extends Rule {

    /**
     * Get the current suspect order amount threshold
     * @return current suspect order amount threshold
     */
    float getSuspectOrderAmountThreshold();

    /**
     * Set the suspect order amount threshold
     * @param suspectOrderAmountThreshold the new suspect order amount threshold
     */
    void setSuspectOrderAmountThreshold(float suspectOrderAmountThreshold);

}
```

Then, we should make our `SuspectOrderRule` implement the `SuspectOrderJmxRule` interface to expose the order amount threshold as a JMX attribute.

So here is the new `SuspectOrderRule` class:

```java
public class SuspectOrderRule extends BasicRule implements SuspectOrderJmxRule {

    // same implementation of the rule

    public float getSuspectOrderAmountThreshold() {
        return suspectOrderAmountThreshold;
    }

    public void setSuspectOrderAmountThreshold(float suspectOrderAmountThreshold) {
        this.suspectOrderAmountThreshold = suspectOrderAmountThreshold;
    }

}
```

Finally, let's suspend the program to change the order amount threshold value at runtime via any compliant JMX client and see the engine behavior after this change:

```java
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
```

In the next screenshot, we use VisualVM to change the threshold value:

<img style="width: 100%" src="{{site.url}}/img/jmx-suspectOrderRule.png">

If you change the threshold value to 1400 for example, you will see that the rule _Suspect Order_ will not be applied the second time since the order amount (1200) is no longer greater than the new threshold (1400).

That's all! In this tutorial, we have seen how to create a real business rule with Easy Rules and how to reconfigure it at runtime.