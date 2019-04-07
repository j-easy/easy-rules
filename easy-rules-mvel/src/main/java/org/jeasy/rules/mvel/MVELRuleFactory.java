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
package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.ActivationRuleGroup;
import org.jeasy.rules.support.CompositeRule;
import org.jeasy.rules.support.ConditionalRuleGroup;
import org.jeasy.rules.support.UnitRuleGroup;
import org.mvel2.ParserContext;

import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * Factory to create {@link MVELRule} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MVELRuleFactory {

    // for backward compatibility only, will be removed in v3.4
    private static final MVELRuleDefinitionReader READER = new MVELYamlRuleDefinitionReader();

    private MVELRuleDefinitionReader reader;

    private static final List<String> ALLOWED_COMPOSITE_RULE_TYPES = Arrays.asList(
            UnitRuleGroup.class.getSimpleName(),
            ConditionalRuleGroup.class.getSimpleName(),
            ActivationRuleGroup.class.getSimpleName()
    );

    /**
     * Create a new {@link MVELRuleFactory} with a given reader.
     *
     * @param reader to use to read rule definitions
     * @see MVELYamlRuleDefinitionReader
     * @see MVELJsonRuleDefinitionReader
     */
    public MVELRuleFactory(MVELRuleDefinitionReader reader) {
        this.reader = reader;
    }

    /**
     * Create a new {@link MVELRule} from a Reader.
     *
     * @param ruleDescriptor as a Reader
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor) throws Exception {
        return createRule(ruleDescriptor, new ParserContext());
    }

    /**
     * Create a new {@link MVELRule} from a Reader.
     *
     * The rule descriptor should contain a single rule definition.
     * If no rule definitions are found, a {@link IllegalArgumentException} will be thrown.
     * If more than a rule is defined in the descriptor, the first rule will be returned.
     *
     * @param ruleDescriptor as a Reader
     * @param parserContext the MVEL parser context
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor, ParserContext parserContext) throws Exception {
        List<MVELRuleDefinition> ruleDefinitions = reader.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0), parserContext);
    }

    /**
     * Create a new {@link MVELRule} using a {@link MVELYamlRuleDefinitionReader}.
     *
     * @param ruleDescriptor as a Reader
     * @return a new rule
     * @deprecated Use {@link MVELRuleFactory#createRule(java.io.Reader)} instead. This method will be removed in v3.4.
     */
    @Deprecated
    public static Rule createRuleFrom(Reader ruleDescriptor) throws Exception {
        return createRuleFrom(ruleDescriptor, new ParserContext());
    }

    /**
     * Create a new {@link MVELRule} using a {@link MVELYamlRuleDefinitionReader}.
     *
     * The rule descriptor should contain a single rule definition.
     * If no rule definitions are found, a {@link IllegalArgumentException} will be thrown.
     * If more than a rule is defined in the descriptor, the first rule will be returned.
     *
     * @param ruleDescriptor as a Reader
     * @param parserContext the MVEL parser context
     * @return a new rule
     * @deprecated Use {@link MVELRuleFactory#createRule(java.io.Reader, org.mvel2.ParserContext)} instead. This method will be removed in v3.4.
     */
    @Deprecated
    public static Rule createRuleFrom(Reader ruleDescriptor, ParserContext parserContext) throws Exception {
        List<MVELRuleDefinition> ruleDefinitions = READER.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0), parserContext);
    }

    /**
     * Create a set of {@link MVELRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor) throws Exception {
        return createRules(rulesDescriptor, new ParserContext());
    }

    /**
     * Create a set of {@link MVELRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor, ParserContext parserContext) throws Exception {
        Rules rules = new Rules();
        List<MVELRuleDefinition> ruleDefinition = reader.read(rulesDescriptor);
        for (MVELRuleDefinition mvelRuleDefinition : ruleDefinition) {
            rules.register(createRule(mvelRuleDefinition, parserContext));
        }
        return rules;
    }

    /**
     * Create a set of {@link MVELRule} using a {@link MVELYamlRuleDefinitionReader}.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     * @deprecated Use {@link MVELRuleFactory#createRules(java.io.Reader)} instead. This method will be removed in v3.4.
     */
    @Deprecated
    public static Rules createRulesFrom(Reader rulesDescriptor) throws Exception {
        return createRulesFrom(rulesDescriptor, new ParserContext());
    }

    /**
     * Create a set of {@link MVELRule} using a {@link MVELYamlRuleDefinitionReader}.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     * @deprecated Use {@link MVELRuleFactory#createRules(java.io.Reader, org.mvel2.ParserContext)} instead. This method will be removed in v3.4.
     */
    @Deprecated
    public static Rules createRulesFrom(Reader rulesDescriptor, ParserContext parserContext) throws Exception {
        Rules rules = new Rules();
        List<MVELRuleDefinition> ruleDefinition = READER.read(rulesDescriptor);
        for (MVELRuleDefinition mvelRuleDefinition : ruleDefinition) {
            rules.register(createRule(mvelRuleDefinition, parserContext));
        }
        return rules;
    }

    private static Rule createRule(MVELRuleDefinition ruleDefinition, ParserContext context) {
        if (ruleDefinition.isCompositeRule()) {
            return createCompositeRule(ruleDefinition, context);
        } else {
            return createSimpleRule(ruleDefinition, context);
        }
    }

    private static Rule createSimpleRule(MVELRuleDefinition ruleDefinition, ParserContext parserContext) {
        MVELRule mvelRule = new MVELRule()
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority())
                .when(ruleDefinition.getCondition(), parserContext);
        for (String action : ruleDefinition.getActions()) {
            mvelRule.then(action, parserContext);
        }
        return mvelRule;
    }

    private static Rule createCompositeRule(MVELRuleDefinition ruleDefinition, ParserContext parserContext) {
        CompositeRule compositeRule;
        String name = ruleDefinition.getName();
        switch (ruleDefinition.getCompositeRuleType()) {
            case "UnitRuleGroup":
                compositeRule = new UnitRuleGroup(name);
                break;
            case "ActivationRuleGroup":
                compositeRule = new ActivationRuleGroup(name);
                break;
            case "ConditionalRuleGroup":
                compositeRule = new ConditionalRuleGroup(name);
                break;
            default:
                throw new IllegalArgumentException("Invalid composite rule type, must be one of " + ALLOWED_COMPOSITE_RULE_TYPES);
        }
        compositeRule.setDescription(ruleDefinition.getDescription());
        compositeRule.setPriority(ruleDefinition.getPriority());

        for (MVELRuleDefinition composingRuleDefinition : ruleDefinition.getComposingRules()) {
            compositeRule.addRule(createRule(composingRuleDefinition, parserContext));
        }

        return compositeRule;
    }
}
