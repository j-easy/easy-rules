/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (md.benhassine@gmail.com)
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

package org.easyrules.jmx.api;

import org.easyrules.api.RulesEngine;

/**
 * Decorates {@link org.easyrules.api.RulesEngine} to add handling of JMX 
 * rules.
 * @param <R> The rule type
 * 
 * @author Drem Darios (drem.darios@gmail.com)
 */
public interface JMXRulesEngine<R> extends RulesEngine<R> {

	/**
     * Register a rule in the rules engine registry.
     * This method also registers the rule as a JMX bean.
     * The rule object <strong>must</strong> be JMX compliant.
     *
     * @param rule the rule to register
     */
    void registerJMXRule(R rule);

    /**
     * Unegister a rule from the rules engine registry.
     * This method also unregisters the rule from MBean server.
     *
     * @param rule the rule to register
     */
    void unregisterJMXRule(R rule);
}
