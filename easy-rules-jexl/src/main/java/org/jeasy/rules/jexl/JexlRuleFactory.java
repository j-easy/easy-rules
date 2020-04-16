/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.jexl;

import java.io.Reader;
import java.util.List;
import java.util.Objects;

import org.apache.commons.jexl3.JexlEngine;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.AbstractRuleFactory;
import org.jeasy.rules.support.RuleDefinition;
import org.jeasy.rules.support.RuleDefinitionReader;

public class JexlRuleFactory extends AbstractRuleFactory<JexlEngine> {

    private RuleDefinitionReader reader;

    public JexlRuleFactory(RuleDefinitionReader reader) {
        this.reader = Objects.requireNonNull(reader, "reader cannot be null");
    }

    public Rule createRule(Reader ruleDescriptor) throws Exception {
        Objects.requireNonNull(ruleDescriptor, "ruleDescriptor cannot be null");
        return createRule(ruleDescriptor, JexlRule.DEFAULT_JEXL);
    }

    public Rule createRule(Reader ruleDescriptor, JexlEngine jexl) throws Exception {
        Objects.requireNonNull(ruleDescriptor, "ruleDescriptor cannot be null");
        Objects.requireNonNull(jexl, "jexl cannot be null");
        List<RuleDefinition> ruleDefinitions = reader.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0), jexl);
    }

    public Rules createRules(Reader rulesDescriptor) throws Exception {
        Objects.requireNonNull(rulesDescriptor, "rulesDescriptor cannot be null");
        return createRules(rulesDescriptor, JexlRule.DEFAULT_JEXL);
    }

    public Rules createRules(Reader rulesDescriptor, JexlEngine jexl) throws Exception {
        Objects.requireNonNull(rulesDescriptor, "rulesDescriptor cannot be null");
        Objects.requireNonNull(jexl, "jexl cannot be null");
        Rules rules = new Rules();
        List<RuleDefinition> ruleDefinitions = reader.read(rulesDescriptor);
        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            rules.register(createRule(ruleDefinition, jexl));
        }
        return rules;
    }

    @Override
    protected Rule createSimpleRule(RuleDefinition ruleDefinition, JexlEngine jexl) {
        Objects.requireNonNull(ruleDefinition, "ruleDefinition cannot be null");
        Objects.requireNonNull(jexl, "jexl cannot be null");
        JexlRule rule = new JexlRule()
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority())
                .when(ruleDefinition.getCondition(), jexl);
        for (String action : ruleDefinition.getActions()) {
            rule.then(action, jexl);
        }
        return rule;
    }
}
