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
package org.jeasy.rules.jscripting;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.support.*;
import javax.script.Bindings;
import java.io.Reader;
import java.util.List;

/**
 * Factory to create {@link JScriptingRule} instances.
 *
 * @author Nabarun Mondal (nabarun.mondal@gmail.com)
 */
public class JScriptingRuleFactory extends AbstractRuleFactory<Bindings> {

    private RuleDefinitionReader reader;

    private String lingo;

    /**
     * Create a new {@link JScriptingRuleFactory} with a given reader.
     *
     * @param reader to use to read rule definitions
     * @see YamlRuleDefinitionReader
     * @see JsonRuleDefinitionReader
     */
    public JScriptingRuleFactory(RuleDefinitionReader reader, String lingo) {
        this.reader = reader;
        this.lingo = lingo;
    }

    /**
     * Create a new {@link JScriptingRule} from a Reader.
     *
     * @param ruleDescriptor as a Reader
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor) throws Exception {
        return createRule(ruleDescriptor, null);
    }

    /**
     * Create a new {@link JScriptingRule} from a Reader.
     *
     * The rule descriptor should contain a single rule definition.
     * If no rule definitions are found, a {@link IllegalArgumentException} will be thrown.
     * If more than a rule is defined in the descriptor, the first rule will be returned.
     *
     * @param ruleDescriptor as a Reader
     * @param context the Scripting context
     * @return a new rule
     */
    public Rule createRule(Reader ruleDescriptor, Bindings context) throws Exception {
        List<RuleDefinition> ruleDefinitions = reader.read(ruleDescriptor);
        if (ruleDefinitions.isEmpty()) {
            throw new IllegalArgumentException("rule descriptor is empty");
        }
        return createRule(ruleDefinitions.get(0), context);
    }

    /**
     * Create a set of {@link JScriptingRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor) throws Exception {
        return createRules(rulesDescriptor, null);
    }

    /**
     * Create a set of {@link JScriptingRule} from a Reader.
     *
     * @param rulesDescriptor as a Reader
     * @return a set of rules
     */
    public Rules createRules(Reader rulesDescriptor, Bindings context) throws Exception {
        Rules rules = new Rules();
        List<RuleDefinition> ruleDefinitions = reader.read(rulesDescriptor);
        for (RuleDefinition ruleDefinition : ruleDefinitions) {
            rules.register(createRule(ruleDefinition, context));
        }
        return rules;
    }

    protected Rule createSimpleRule(RuleDefinition ruleDefinition, Bindings context) {
        JScriptingRule jScriptingRule = new JScriptingRule(lingo)
                .name(ruleDefinition.getName())
                .description(ruleDefinition.getDescription())
                .priority(ruleDefinition.getPriority())
                .when(ruleDefinition.getCondition(), context);
        for (String action : ruleDefinition.getActions()) {
            jScriptingRule.then(action, context);
        }
        return jScriptingRule;
    }

}
