package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.core.BasicRule;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link org.jeasy.rules.api.Rule} implementation that uses <a href="https://github.com/mvel/mvel">MVEL</a> to evaluate and execute the rule.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MVELRule extends BasicRule {

    private Condition condition = Condition.FALSE;
    private List<Action> actions = new ArrayList<>();

    /**
     * Create a new MVEL action.
     */
    public MVELRule() {
    }

    /**
     * Create a new MVEL action.
     *
     * @param name of the rule
     */
    public MVELRule(String name) {
        super(name);
    }

    /**
     * Create a new MVEL action.
     * @param name of the rule
     * @param description of the rule
     */
    public MVELRule(String name, String description) {
        super(name, description);
    }

    /**
     * Create a new MVEL action.
     * @param name of the rule
     * @param description of the rule
     * @param priority of the rule
     */
    public MVELRule(String name, String description, int priority) {
        super(name, description, priority);
    }

    /**
     * Specify the rule's condition as MVEL expression.
     * @param condition of the rule
     * @return this rule
     */
    public MVELRule when(String condition) {
        this.condition = new MVELCondition(condition);
        return this;
    }

    /**
     * Add an action specified as an MVEL expression to the rule.
     * @param action to add to the rule
     * @return this rule
     */
    public MVELRule then(String action) {
        this.actions.add(new MVELAction(action));
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}
