package org.jeasy.rules.tutorial.springboot.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.starter.support.EasyRulesTemplate;
import org.jeasy.rules.starter.support.RuleGroup;
import org.jeasy.rules.tutorial.springboot.pojo.MyOrder;

/**
 * first rule
 *
 * @author venus
 * @version 1
 */
@RuleGroup(name = "demo")
@Rule(name = "first", description = "first rule", priority = 1)
public class MyFirstRule {

    @Condition
    public boolean evaluate(@Fact(EasyRulesTemplate.DEFAULT_FACT_NAME) MyOrder myOrder) {
        return myOrder.isCustomerOrder();
    }

    @Action(order = 1)
    public void doAction1() {
        System.out.println("Customer order action one");
    }

    @Action(order = 2)
    public void doAction2() {
        System.out.println("Customer order action two");
    }
}
