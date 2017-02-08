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
package org.easyrules.api;

/**
 * A listener for rules execution events.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public interface RuleListener {

    /**
     * Triggered before the evaluation of a rule.
     *
     * @param rule being evaluated
     * @return true if the rule should be evaluated, false otherwise
     */
    boolean beforeEvaluate(Rule rule);

    /**
     * Triggered before the execution of a rule.
     *
     * @param rule the current rule
     */
    void beforeExecute(Rule rule);

    /**
     * Triggered after a rule has been executed successfully.
     *
     * @param rule the current rule
     */
    void onSuccess(Rule rule);

    /**
     * Triggered after a rule has failed.
     *
     * @param rule      the current rule
     * @param exception the exception thrown when attempting to execute the rule
     */
    void onFailure(Rule rule, Exception exception);

}
