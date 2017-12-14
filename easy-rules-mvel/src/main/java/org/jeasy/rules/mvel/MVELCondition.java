package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * This class is an implementation of {@link Condition} that uses <a href="https://github.com/mvel/mvel">MVEL</a> to evaluate the condition.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MVELCondition implements Condition {

    private static Logger LOGGER = LoggerFactory.getLogger(MVELCondition.class);

    private String expression;
    private Serializable compiledExpression;

    /**
     * Create a new {@link MVELCondition}.
     *
     * @param expression the condition written in expression language
     */
    public MVELCondition(String expression) {
        this.expression = expression;
        compiledExpression = MVEL.compileExpression(expression);
    }

    @Override
    public boolean evaluate(Facts facts) {
        try {
            return (boolean) MVEL.executeExpression(compiledExpression, facts.asMap());
        } catch (Exception e) {
            LOGGER.debug("Unable to evaluate expression: '" + expression + "' on facts: " + facts, e);
            return false;
        }
    }
}
