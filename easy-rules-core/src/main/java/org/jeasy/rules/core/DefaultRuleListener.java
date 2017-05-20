/**
 * The MIT License
 *
 *  Copyright (c) 2017, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.core;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

import java.util.logging.Level;
import java.util.logging.Logger;

class DefaultRuleListener implements RuleListener {

    private static final Logger LOGGER = Logger.getLogger(DefaultRuleListener.class.getName());

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        return true;
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        String ruleName = rule.getName();
        if (evaluationResult) {
            LOGGER.log(Level.INFO, "Rule ''{0}'' triggered", ruleName);
        } else {
            LOGGER.log(Level.INFO, "Rule ''{0}'' has been evaluated to false, it has not been executed", ruleName);
        }
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {

    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        LOGGER.log(Level.INFO, "Rule ''{0}'' performed successfully", rule.getName());
    }

    @Override
    public void onFailure(Rule rule, Facts facts, Exception exception) {
        LOGGER.log(Level.SEVERE, String.format("Rule '%s' performed with error", rule.getName()), exception);
    }
}
