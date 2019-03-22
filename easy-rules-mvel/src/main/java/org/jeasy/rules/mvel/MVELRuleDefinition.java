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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MVELRuleDefinition {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;
    private String condition;
    private List<String> actions;
    private Rules subrules;
    private String compositeRuleType;
    private List<String> allowedCompositeTypes = new ArrayList<>(
            Arrays.asList("UnitRuleGroup", "ConditionalRuleGroup", "ActivationRuleGroup")
    );

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public void setSubrules(List<MVELRuleDefinition> subruleDefinitions, String compositeRuleType) {
        subrules = new Rules();
        for (MVELRuleDefinition ruleDef : subruleDefinitions) {
            Rule r = ruleDef.create();
            subrules.register(r);
        }
        setCompositeRuleType(compositeRuleType);
    }

    public void setCompositeRuleType(String compositeRuleType) { this.compositeRuleType = compositeRuleType; }

    public String getCompositeRuleType() { return compositeRuleType; }

    public Rules getSubrules() { return subrules; }

    Rule create() {
        if (subrules == null) {
            MVELRule mvelRule = new MVELRule()
                    .name(name)
                    .description(description)
                    .priority(priority)
                    .when(condition);
            for (String action : actions) {
                mvelRule.then(action);
            }
            return mvelRule;
        } else {
            if (allowedCompositeTypes.contains(compositeRuleType)) {
                final CompositeRule compositeRule;

                switch (compositeRuleType) {
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
                        throw new IllegalArgumentException("Invalid composite rule type");
                }

                compositeRule.setDescription(description);
                compositeRule.setPriority(priority);

                for (Rule rule : subrules) {
                    compositeRule.addRule(rule);
                }

                return compositeRule;
            } else {
                throw new IllegalArgumentException("Invalid composite rule type");
            }
        }
    }
}