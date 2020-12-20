package org.jeasy.rules.tutorial.springboot.rules;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.starter.support.EasyRulesTemplate;
import org.jeasy.rules.starter.support.RuleGroup;
import org.jeasy.rules.tutorial.springboot.pojo.MyOrder;

/**
 * second rule
 *
 * @author venus
 * @version 1
 */
@RuleGroup(name = "demo")
@Rule(name = "second", description = "second rule", priority = 2)
public class MySecondRule {

    @Condition
    public boolean evaluate(@Fact(EasyRulesTemplate.DEFAULT_FACT_NAME) MyOrder myOrder) {
        // purchase order
        return !myOrder.isCustomerOrder();
    }

    @Action
    public void doPurchaseOrderAction(@Fact(EasyRulesTemplate.DEFAULT_FACT_NAME) MyOrder myOrder) {
        System.out.println(myOrder);
        System.out.println("Purchase Order Action");
    }
}
