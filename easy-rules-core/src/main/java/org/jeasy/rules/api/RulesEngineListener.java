/**
 * The MIT License
 *
 *  Copyright (c) 2018, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.api;

import org.jeasy.rules.core.InferenceRulesEngine;

/**
 * A listener for rules engine execution events.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RulesEngineListener {

    /**
     * Triggered before evaluating the rule set.
     * <strong>When this listener is used with a {@link InferenceRulesEngine}, this method will be triggered before the evaluation of each candidate rule set in each iteration.</strong>
     *
     * @param rules to fire
     * @param facts present before firing rules
     */
    void beforeEvaluate(Rules rules, Facts facts);

    /**
     * Triggered after executing the rule set
     * <strong>When this listener is used with a {@link InferenceRulesEngine}, this method will be triggered after the execution of each candidate rule set in each iteration.</strong>
     *
     * @param rules fired
     * @param facts present after firing rules
     */
    void afterExecute(Rules rules, Facts facts);
}