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
package org.jeasy.rules.jexl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;

/**
 * @author Lauri Kimmel
 * @author Mahmoud Ben Hassine
 */
public class JexlRule extends BasicRule {

    static final JexlEngine DEFAULT_JEXL = new JexlBuilder().create();

    private Condition condition = Condition.FALSE;
    private final List<Action> actions = new ArrayList<>();
    private final JexlEngine jexl;

    public JexlRule() {
        this(DEFAULT_JEXL);
    }

    public JexlRule(JexlEngine jexl) {
        super(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
        this.jexl = Objects.requireNonNull(jexl, "jexl cannot be null");
    }

    public JexlRule name(String name) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        return this;
    }

    public JexlRule description(String description) {
        this.description = Objects.requireNonNull(description, "description cannot be null");
        return this;
    }

    public JexlRule priority(int priority) {
        this.priority = priority;
        return this;
    }

    public JexlRule when(String condition) {
        Objects.requireNonNull(condition, "condition cannot be null");
        this.condition = new JexlCondition(condition, jexl);
        return this;
    }

    public JexlRule then(String action) {
        Objects.requireNonNull(action, "action cannot be null");
        this.actions.add(new JexlAction(action, jexl));
        return this;
    }

    @Override
    public boolean evaluate(Facts facts) {
        Objects.requireNonNull(facts, "facts cannot be null");
        return condition.evaluate(facts);
    }

    @Override
    public void execute(Facts facts) throws Exception {
        Objects.requireNonNull(facts, "facts cannot be null");
        for (Action action : actions) {
            action.execute(facts);
        }
    }
}
