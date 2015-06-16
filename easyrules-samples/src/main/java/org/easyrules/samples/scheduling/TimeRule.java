package org.easyrules.samples.scheduling;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import java.util.Date;

@Rule(name = "time rule", description = "Print the current time only if when minutes are even")
public class TimeRule {

    private Date now;

    @Condition
    public boolean checkTime() {
        now = new Date();
        return now.getMinutes() % 2 == 0;
    }

    @Action
    public void printTime() {
        System.out.println(now);
    }

}
