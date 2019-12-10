package org.jeasy.rules.tutorials.airco;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;

public class DecreaseTemperatureAction implements Action {

    static DecreaseTemperatureAction decreaseTemperature() {
        return new DecreaseTemperatureAction();
    }

    @Override
    public Object execute(Facts facts) throws Exception {
        System.out.println("It is hot! cooling air..");
        Integer temperature = facts.get("temperature");
        facts.put("temperature", temperature - 1);
        return null;
    }
}
