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

import com.googlecode.aviator.Expression;
import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jeasy.rules.aviator.AviatorRule.DEFAULT_AVIATOR;

/**
 * author peterxiemin(bbcoder1987@gmail.com)
 */
public class AviatorCondition implements Condition {
    private static final Logger LOGGER = LoggerFactory.getLogger(AviatorCondition.class.getName());

    private final String expression;

    private final Expression compileExpression;


    /**
     * Create a new {@link AviatorCondition}.
     * @param expression
     * the condition written in expression language
     */
    public AviatorCondition(String expression) {
        this.expression = expression;
        this.compileExpression = DEFAULT_AVIATOR.compile(expression);
    }

    /**
     * Evaluate the rule.
     * @param facts known when evaluating the rule.
     * @return true if the rule should be applied, false otherwise.
     */
    @Override
    public boolean evaluate(Facts facts) {
        return (Boolean) compileExpression.execute(facts.asMap());
    }
}
