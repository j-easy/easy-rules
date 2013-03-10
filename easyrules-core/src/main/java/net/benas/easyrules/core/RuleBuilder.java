/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.easyrules.core;

import net.benas.easyrules.api.ActionPerformer;
import net.benas.easyrules.api.ConditionTrigger;
import net.benas.easyrules.util.DummyConditionTrigger;
import net.benas.easyrules.util.EasyRulesConstants;
import net.benas.easyrules.util.NoOpActionPerformer;

/**
 * A rule builder that builds {@link Rule} instances with sensible defaults defined in {@link EasyRulesConstants}.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class RuleBuilder {

    private Rule rule;

    public RuleBuilder() {
        rule = new Rule(EasyRulesConstants.DEFAULT_RULE_NAME, EasyRulesConstants.DEFAULT_RULE_DESCRIPTION,
                EasyRulesConstants.DEFAULT_RULE_PRIORITY, new DummyConditionTrigger(), new NoOpActionPerformer());
    }

    /**
     * Set rule name.
     * @param name rule name
     * @return the rule builder instance
     */
   public RuleBuilder name(String name) {
       rule.setName(name);
       return this;
   }

    /**
     * Set rule description.
     * @param description rule description
     * @return the rule builder instance
     */
    public RuleBuilder description(String description) {
        rule.setDescription(description);
        return this;
    }

    /**
     * Set rule priority.
     * @param priority rule priority
     * @return the rule builder instance
     */
    public RuleBuilder priority(int priority) {
        rule.setPriority(priority);
        return this;
    }

    /**
     * Set rule condition trigger.
     * @param conditionTrigger rule condition trigger
     * @return the rule builder instance
     */
    public RuleBuilder conditionTrigger(ConditionTrigger conditionTrigger) {
        rule.setConditionTrigger(conditionTrigger);
        return this;
    }

    /**
     * Set rule action performer.
     * @param actionPerformer rule action performer
     * @return the rule builder instance
     */
    public RuleBuilder actionPerformer(ActionPerformer actionPerformer) {
        rule.setActionPerformer(actionPerformer);
        return this;
    }

    /**
     * Build a {@link Rule} configured instance.
     * @return a properly configured instance
     */
    public Rule build() {
        return rule;
    }

}
