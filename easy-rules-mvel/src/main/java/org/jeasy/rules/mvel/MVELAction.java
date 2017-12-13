package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is an implementation of {@link Action} that uses <a href="https://github.com/mvel/mvel">MVEL</a> to execute the action.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MVELAction implements Action {

    private static Logger LOGGER = LoggerFactory.getLogger(MVELAction.class);

    private String expression;

    /**
     * Create a new {@link MVELAction}.
     *
     * @param expression the action written in expression language
     */
    public MVELAction(String expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Facts facts) {
        try {
            MVEL.eval(expression, facts.asMap());
        } catch (Exception e) {
            LOGGER.debug("Unable to evaluate expression: '" + expression + "' on facts: " + facts, e);
        }
    }
}
