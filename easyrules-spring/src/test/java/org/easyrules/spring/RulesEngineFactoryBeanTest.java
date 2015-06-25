/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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
import org.easyrules.core.BasicRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link RulesEngineFactoryBean}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesEngineFactoryBeanTest {

    public static final int RULE_PRIORITY_THRESHOLD = 10;

    @Mock
    BasicRule rule;

    @Mock
    private RuleListener ruleListener;

    private int rulePriorityThreshold;

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstFailedRule;
    
    private boolean silentMode;
    
    private RulesEngineFactoryBean rulesEngineFactoryBean;

    @Before
    public void setUp() {
        silentMode = true;
        skipOnFirstFailedRule = true;
        skipOnFirstAppliedRule = true;
        rulePriorityThreshold = RULE_PRIORITY_THRESHOLD;
        rulesEngineFactoryBean = new RulesEngineFactoryBean();
    }

    @Test
    public void getObject() {
        rulesEngineFactoryBean.setRules(Collections.<Object>singletonList(rule));
        rulesEngineFactoryBean.setRuleListeners(singletonList(ruleListener));
        rulesEngineFactoryBean.setRulePriorityThreshold(rulePriorityThreshold);
        rulesEngineFactoryBean.setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        rulesEngineFactoryBean.setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        rulesEngineFactoryBean.setSilentMode(silentMode);
        RulesEngine rulesEngine = rulesEngineFactoryBean.getObject();

        assertThat(rulesEngine).isNotNull();
        // TODO assert using reflection that fields are correctly injected
    }

    @Test
    public void getObjectType() {
        assertThat(rulesEngineFactoryBean.getObjectType()).isEqualTo(RulesEngine.class);
    }

    @Test
    public void isSingleton() {
        assertThat(rulesEngineFactoryBean.isSingleton()).isFalse();
    }
}