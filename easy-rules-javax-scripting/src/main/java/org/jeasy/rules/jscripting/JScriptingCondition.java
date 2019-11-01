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

import org.jeasy.rules.api.Condition;
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;


/**
 * This class is an implementation of {@link Condition} that uses
 * *<a href="https://docs.oracle.com/javase/9/docs/api/javax/script/ScriptEngine.html">ScriptEngineL</a>
 *
 * @author Nabarun Mondal (nabarun.mondal@gmail.com)
 */
public class JScriptingCondition implements Condition {

    private static final Logger LOGGER = LoggerFactory.getLogger(JScriptingCondition.class);
    private JScriptingHandler jScriptingHandler;

    /**
     * Create a new {@link JScriptingCondition}.
     *
     * @param expression the condition written in expression language
     */
    public JScriptingCondition(String lingo, String expression) {
        this.jScriptingHandler = new JScriptingHandler(lingo,expression,null);
    }

    /**
     * Create a new {@link JScriptingCondition}.
     *
     * @param expression    the condition written in expression language
     * @param  context Scripting context
     */
    public JScriptingCondition(String lingo, String expression, Bindings context) {
        this.jScriptingHandler = new JScriptingHandler(lingo,expression,context);
    }

    @Override
    public boolean evaluate(Facts facts) {
        try {
            return jScriptingHandler.test(facts.asMap());
        } catch (Exception e) {
            LOGGER.error("Unable to evaluate expression: '" + jScriptingHandler.expression + "' on facts: " + facts, e);
            return false;
        }
    }
}
