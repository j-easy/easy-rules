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
package org.easyrules.spring;

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.beans.factory.FactoryBean;

import java.util.List;

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine;
import static org.easyrules.util.Utils.DEFAULT_ENGINE_NAME;
import static org.easyrules.util.Utils.DEFAULT_RULE_PRIORITY_THRESHOLD;

/**
 * Factory bean to create {@link RulesEngine} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineFactoryBean implements FactoryBean<RulesEngine> {

    private String name = DEFAULT_ENGINE_NAME;

    private int priorityThreshold = DEFAULT_RULE_PRIORITY_THRESHOLD;
    
    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstNonTriggeredRule;

    private boolean skipOnFirstFailedRule;
    
    private boolean silentMode;
    
    private List<Object> rules;

    private List<RuleListener> ruleListeners;

    @Override
    public RulesEngine getObject() {
        RulesEngineBuilder rulesEngineBuilder = aNewRulesEngine()
                .named(name)
                .withSkipOnFirstAppliedRule(skipOnFirstAppliedRule)
                .withSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule)
                .withSkipOnFirstFailedRule(skipOnFirstFailedRule)
                .withRulePriorityThreshold(priorityThreshold)
                .withSilentMode(silentMode);
        registerRuleListeners(rulesEngineBuilder);
        RulesEngine rulesEngine = rulesEngineBuilder.build();
        registerRules(rulesEngine);
        return rulesEngine;
    }

    @Override
    public Class<RulesEngine> getObjectType() {
        return RulesEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private void registerRules(RulesEngine rulesEngine) {
        if (rules != null && !rules.isEmpty()) {
            for (Object rule : rules) {
                rulesEngine.registerRule(rule);
            }
        }
    }

    private void registerRuleListeners(RulesEngineBuilder rulesEngineBuilder) {
        if (ruleListeners != null && !ruleListeners.isEmpty()) {
            for (RuleListener ruleListener : ruleListeners) {
                rulesEngineBuilder.withRuleListener(ruleListener);
            }
        }
    }

    /*
     * Setters for dependency injection. 
     */

    public void setRuleListeners(List<RuleListener> ruleListeners) {
        this.ruleListeners = ruleListeners;
    }

    public void setRules(List<Object> rules) {
        this.rules = rules;
    }

    public void setPriorityThreshold(int priorityThreshold) {
        this.priorityThreshold = priorityThreshold;
    }

    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
    }

    public void setSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    public void setSkipOnFirstNonTriggeredRule(boolean skipOnFirstNonTriggeredRule) {
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
    }

    public void setSkipOnFirstFailedRule(boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
    }

    public void setName(String name) {
        this.name = name;
    }
}
