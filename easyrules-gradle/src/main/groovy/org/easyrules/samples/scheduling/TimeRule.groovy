package org.easyrules.samples.scheduling

import org.easyrules.annotation.Action
import org.easyrules.annotation.Condition
import org.easyrules.annotation.Rule

import java.util.Date

@Rule(name = "time rule", description = "Print the current time only if seconds are even")
class TimeRule {

    def now

    @Condition
    boolean when() {
        now = new Date()
        now.getSeconds() % 2 == 0
    }

    @Action
    void then() { println "Seconds in ${now.getSeconds()} are even" }

}
