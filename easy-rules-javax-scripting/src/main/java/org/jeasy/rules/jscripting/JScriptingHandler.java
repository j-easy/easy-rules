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

import javax.script.*;
import java.util.Map;

public class JScriptingHandler {

    public static final String DEFAULT_JVM_LINGO = "nashorn" ;

    public final String expression;
    public final String lingo;
    public final ScriptEngine scriptEngine;
    public final Bindings context;

    public JScriptingHandler(String lingo, String expression, Bindings context) {
        if (lingo == null) {
            lingo = DEFAULT_JVM_LINGO;
        }
        scriptEngine = new ScriptEngineManager().getEngineByName(lingo);
        this.expression = expression;
        this.lingo = lingo;
        if (context == null) {
            context = new SimpleBindings();
        }
        this.context = context;
    }

    public Object eval(Map<String, Object> otherContext) throws Exception {
        if (otherContext != null) {
            context.putAll(otherContext);
        }
        return scriptEngine.eval(expression, context);
    }

    public boolean test(Map<String, Object> otherContext) throws Exception {
        Object r = eval(otherContext);
        if (r instanceof Boolean) return (boolean) r;
        throw new UnsupportedOperationException("expression returned non boolean!");
    }

}
