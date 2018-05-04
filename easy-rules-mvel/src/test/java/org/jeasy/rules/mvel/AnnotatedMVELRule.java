package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;


@org.jeasy.rules.annotation.Rule(name = "myRule", description = "my rule description")
public class AnnotatedMVELRule extends MVELRule {

    private boolean executed;
    private boolean evaluated;

    public boolean evaluate(Facts facts) {
        evaluated = true;
        return super.evaluate(facts);
    }

    public void execute(Facts facts) throws Exception {
        super.execute(facts);
        executed = true;
    }

    public boolean isExecuted() { return executed; }

    public boolean isEvaluated() { return evaluated; }

}
