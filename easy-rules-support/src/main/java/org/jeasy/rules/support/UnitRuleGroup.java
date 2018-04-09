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
package org.jeasy.rules.support;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * A unit rule group is a composite rule that acts as a unit: Either all rules are applied or nothing is applied.
 *
 *  @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class UnitRuleGroup extends CompositeRule {

    /**
     * Create a unit rule group.
     */
    public UnitRuleGroup() {
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     */
    public UnitRuleGroup(String name) {
        super(name);
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     * @param description of the composite rule
     */
    public UnitRuleGroup(String name, String description) {
        super(name, description);
    }

    /**
     * Create a unit rule group.
     * @param name of the composite rule
     * @param description of the composite rule
     * @param priority of the composite rule
     */
    public UnitRuleGroup(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluate(Facts facts) {
        if (!rules.isEmpty()) {
            for (Rule rule : rules) {
                if (!rule.evaluate(facts)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        for (Rule rule : rules) {
            rule.execute(facts);
        }
    }
}
