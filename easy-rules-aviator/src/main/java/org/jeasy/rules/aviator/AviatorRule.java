/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.runtime.JavaMethodReflectionFunctionMissing;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;

import java.util.ArrayList;
import java.util.List;

/**
 * author peterxiemin(bbcoder1987@gmail.com)
 */
public class AviatorRule extends BasicRule {
    public static final AviatorEvaluatorInstance DEFAULT_AVIATOR;

    static {
        DEFAULT_AVIATOR = AviatorEvaluator.getInstance();
        DEFAULT_AVIATOR.setFunctionMissing(JavaMethodReflectionFunctionMissing.getInstance());
    }

    private Condition condition = Condition.FALSE;

    private final List<Action> actions = new ArrayList<>();

    /**
     * Create a new Aviator rule.
     */
    public AviatorRule() {
        super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
    }

    /**
     * set rule name
     * @param name
     * @return this rule
     */
    public AviatorRule name(String name) {
        this.name = name;
        return this;
    }

    /**
     * set rule description
     * @param description
     * @return this rule
     */
    public AviatorRule description(String description) {
        this.description = description;
        return this;
    }

    /**
     * set rule priority
     * @param priority
     * @return this rule
     */
    public AviatorRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * set rule condition
     * @param condition
     * @return this rule
     */
    public AviatorRule when(String condition) {
        this.condition = new AviatorCondition(condition);
        return this;
    }

    /**
     * set rule action
     * @param action
     * @return this rule
     */
    public AviatorRule then(String action) {
        actions.add(new AviatorAction(action));
        return this;
    }

    /**
     * evaluate the rule
     * @param facts
     * @return
     */
    @Override
    public boolean evaluate(Facts facts) {
        return condition.evaluate(facts);
    }

    /**
     * execute the rule
     * @param facts
     * @throws Exception
     */
    @Override
    public void execute(Facts facts) throws Exception {
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}
