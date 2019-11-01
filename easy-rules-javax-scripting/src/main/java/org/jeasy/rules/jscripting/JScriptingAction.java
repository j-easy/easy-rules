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
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.*;


/**
 * This class is an implementation of {@link Action} that uses
 * <a href="https://docs.oracle.com/javase/9/docs/api/javax/script/ScriptEngine.html">ScriptEngineL</a>
 * to execute the action.
 * @author Nabarun Mondal (nabarun.mondal@gmail.com)
 */
public class JScriptingAction implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(JScriptingAction.class);
    private JScriptingHandler jScriptingHandler;

    /**
     * Create a new {@link JScriptingAction}.
     *
     * @param expression the action written in expression language
     */
    public JScriptingAction(String lingo, String expression,  Bindings context) {
        jScriptingHandler = new JScriptingHandler(lingo,expression,context);
    }
    /**
     * Create a new {@link JScriptingAction}.
     *
     * @param expression the action written in expression language
     */
    public JScriptingAction(String lingo, String expression) {
        this(lingo,expression, null);
    }

    @Override
    public void execute(Facts facts) throws Exception{
        try {
            jScriptingHandler.eval( facts.asMap());
        } catch (Exception e) {
            LOGGER.error("Unable to evaluate expression: '" + jScriptingHandler.expression + "' on facts: " + facts, e);
            throw e;
        }
    }
}
