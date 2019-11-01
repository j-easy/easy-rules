/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.jeasy.rules.jscripting;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;

import javax.script.Bindings;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link org.jeasy.rules.api.Rule} implementation that uses
 * <a href="https://docs.oracle.com/javase/9/docs/api/javax/script/ScriptEngine.html">ScriptEngineL</a>
 * to evaluate and execute the rule.
 * @author Nabarun Mondal (nabarun.mondal@gmail.com)
 */
public class JScriptingRule extends BasicRule {

    private Condition condition = Condition.FALSE;
    private List<Action> actions = new ArrayList<>();
    private String lingo;

    /**
     * Create a new JScripting rule.
     */
    public JScriptingRule(String lingo) {
        super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
        this.lingo = lingo;
    }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public JScriptingRule name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public JScriptingRule description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public JScriptingRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Specify the rule's condition as MVEL expression.
     * @param condition of the rule
     * @return this rule
     */
    public JScriptingRule when(String condition) {
        return this.when(condition, null);
    }

    /**
     * Specify the rule's condition as MVEL expression.
     * @param condition of the rule
     * @param context the Scripting context
     * @return this rule
     */
    public JScriptingRule when(String condition, Bindings context) {
        this.condition = new JScriptingCondition(lingo, condition, context);
        return this;
    }

    /**
     * Add an action specified as an MVEL expression to the rule.
     * @param action to add to the rule
     * @return this rule
     */
    public JScriptingRule then(String action) {
        return this.then(action, null);
    }

    /**
     * Add an action specified as an MVEL expression to the rule.
     * @param action to add to the rule
     * @param context the MVEL parser context
     * @return this rule
     */
    public JScriptingRule then(String action, Bindings context) {
        this.actions.add(new JScriptingAction(lingo, action, context));
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
