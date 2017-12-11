package org.jeasy.rules.tutorials.airco;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "air conditioning rule", description = "if it is hot, decrease temperature" )
public class AirConditioningRule {

    @Condition
    public boolean isItHot(@Fact("temperature") int temperature) {
        return temperature > 25;
    }

    @Action
    public void coolAir(Facts facts) {
        System.out.println("It is hot! cooling air..");
        Integer temperature = (Integer) facts.get("temperature");
        facts.put("temperature", temperature - 1);
    }

}
