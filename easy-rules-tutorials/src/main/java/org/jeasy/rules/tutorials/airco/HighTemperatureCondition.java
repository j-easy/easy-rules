package org.jeasy.rules.tutorials.airco;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;

public class HighTemperatureCondition implements Condition {

    static HighTemperatureCondition itIsHot() {
        return new HighTemperatureCondition();
    }

    @Override
    public boolean evaluate(Facts facts) {
        Integer temperature = facts.get("temperature");
        return temperature > 25;
    }

}
