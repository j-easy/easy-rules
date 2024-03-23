/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.aviator;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.AbstractRuleFactory;
import org.jeasy.rules.support.RuleDefinition;
import org.jeasy.rules.support.reader.JsonRuleDefinitionReader;
import org.jeasy.rules.support.reader.RuleDefinitionReader;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;

import java.io.Reader;
import java.util.List;

/**
 * author peterxiemin(bbcoder1987@gmail.com)
 */
public class AviatorRuleFactory extends AbstractRuleFactory {
    private final RuleDefinitionReader reader;

    /**
     * Create a new {@link AviatorRuleFactory} with a given reader.
     *
     * @param reader used to read rule definitions
     * @see YamlRuleDefinitionReader
     * @see JsonRuleDefinitionReader
     */
    public AviatorRuleFactory(RuleDefinitionReader reader) {
        this.reader = reader;
    }


    /**
     * Create a new {@link AviatorRule} from a Reader.
     *
     * The rule descriptor should contain a single rule definition.
     * If no rule definitions are found, a {@link IllegalArgumentException} will be thrown.
     * If more than a rule is defined in the descriptor, the first rule will be returned.
     *
     * @param ruleDescriptor descriptor of rule definition
     * @return a new rule
     * @throws Exception if unable to create the rule from the descriptor
     */
    public Rule createRule(Reader ruleDescriptor) throws Exception {
        List<RuleDefinition> ruleDefinitions = reader.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0));
    }

    /**
     * Create a new {@link AviatorRule} from a {@link RuleDefinition}.
     *
     * @param rulesDescriptor definition of the rule
     * @return a new rule
     */
    public Rules createRules(Reader rulesDescriptor) throws Exception {
        Rules rules = new Rules();
        List<RuleDefinition> ruleDefinitions = reader.read(rulesDescriptor);
        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            rules.register(createRule(ruleDefinition));
        }
        return rules;
    }

    /**
     * Create a simple rule from a rule definition.
     * @param ruleDefinition
     * @return
     */
    @Override
    protected Rule createSimpleRule(RuleDefinition ruleDefinition) {
        AviatorRule aviatorRule = new AviatorRule()
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority())
                .when(ruleDefinition.getCondition());
        for (String action : ruleDefinition.getActions()) {
            aviatorRule.then(action);
        }
        return aviatorRule;
    }
}
