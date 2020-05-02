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
package org.jeasy.rules.spel;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.*;

import org.springframework.expression.BeanResolver;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;

import java.io.Reader;
import java.util.List;

/**
 * Factory to create {@link SpELRule} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SpELRuleFactory extends AbstractRuleFactory<ParserContext> {

    private RuleDefinitionReader reader;
    private BeanResolver beanResolver;

    /**
     * Create a new {@link SpELRuleFactory} with a given reader.
     *
     * @param reader to use to read rule definitions
     * @see YamlRuleDefinitionReader
     * @see JsonRuleDefinitionReader
     */
    public SpELRuleFactory(RuleDefinitionReader reader) {
        this.reader = reader;
    }

    /**
     * Create a new {@link SpELRuleFactory} with a given reader.
     *
     * @param reader to use to read rule definitions
     * @param beanResolver to use to resolve bean references
     * @see YamlRuleDefinitionReader
     * @see JsonRuleDefinitionReader
     */
    public SpELRuleFactory(RuleDefinitionReader reader, BeanResolver beanResolver) {
        this(reader);
        this.beanResolver = beanResolver;
    }

    /**
     * Create a new {@link SpELRule} from a Reader.
     *
     * @param ruleDescriptor as a Reader
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor) throws Exception {
        return createRule(ruleDescriptor, ParserContext.TEMPLATE_EXPRESSION);
    }

    /**
     * Create a new {@link SpELRule} from a Reader.
     *
     * The rule descriptor should contain a single rule definition.
     * If no rule definitions are found, a {@link IllegalArgumentException} will be thrown.
     * If more than a rule is defined in the descriptor, the first rule will be returned.
     *
     * @param ruleDescriptor as a Reader
     * @param parserContext the SpEL parser context
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor, ParserContext parserContext) throws Exception {
        List<RuleDefinition> ruleDefinitions = reader.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0), parserContext);
    }

    /**
     * Create a set of {@link SpELRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor) throws Exception {
        return createRules(rulesDescriptor, ParserContext.TEMPLATE_EXPRESSION);
    }

    /**
     * Create a set of {@link SpELRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor, ParserContext parserContext) throws Exception {
        Rules rules = new Rules();
        List<RuleDefinition> ruleDefinitions = reader.read(rulesDescriptor);
        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            rules.register(createRule(ruleDefinition, parserContext));
        }
        return rules;
    }

    protected Rule createSimpleRule(RuleDefinition ruleDefinition, ParserContext parserContext) {
        SpELRule spELRule = new SpELRule()
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority());
        if (beanResolver != null) {
            spELRule.when(ruleDefinition.getCondition(), parserContext, beanResolver);
            for (String action : ruleDefinition.getActions()) {
                spELRule.then(action, parserContext, beanResolver);
            }
        } else {
            spELRule.when(ruleDefinition.getCondition(), parserContext);
            for (String action : ruleDefinition.getActions()) {
                spELRule.then(action, parserContext);
            }
        }
        return spELRule;
    }

}
