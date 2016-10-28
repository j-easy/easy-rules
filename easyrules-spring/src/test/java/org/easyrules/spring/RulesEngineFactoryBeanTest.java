/*
 * The MIT License
 *
 *  Copyright (c) 2016, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
import org.easyrules.core.RulesEngineParameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link RulesEngineFactoryBean}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesEngineFactoryBeanTest {

    public static final String NAME = "myRulesEngine";
    public static final int RULE_PRIORITY_THRESHOLD = 10;

    @Mock
    BasicRule rule;

    @Mock
    private RuleListener ruleListener;

    private String name;

    private int priorityThreshold;

    private boolean skipOnFirstAppliedRule;

    private boolean skipOnFirstNonTriggeredRule;

    private boolean skipOnFirstFailedRule;

    private boolean silentMode;

    private RulesEngineFactoryBean rulesEngineFactoryBean;

    @Before
    public void setUp() {
        name = NAME;
        silentMode = true;
        skipOnFirstAppliedRule = true;
        skipOnFirstNonTriggeredRule = true;
        skipOnFirstFailedRule = true;
        priorityThreshold = RULE_PRIORITY_THRESHOLD;
        rulesEngineFactoryBean = new RulesEngineFactoryBean();
    }

    @SuppressWarnings({"AssertEqualsBetweenInconvertibleTypes", "unchecked"})
    @Test
    public void getObject() {
        List<Object> expectedRules = Collections.<Object>singletonList(rule);
        List<RuleListener> expectedRuleListeners = singletonList(ruleListener);

        rulesEngineFactoryBean.setRules(expectedRules);
        rulesEngineFactoryBean.setRuleListeners(expectedRuleListeners);
        rulesEngineFactoryBean.setPriorityThreshold(priorityThreshold);
        rulesEngineFactoryBean.setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        rulesEngineFactoryBean.setSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule);
        rulesEngineFactoryBean.setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        rulesEngineFactoryBean.setSilentMode(silentMode);
        rulesEngineFactoryBean.setName(name);
        RulesEngine rulesEngine = rulesEngineFactoryBean.getObject();

        assertThat(rulesEngine).isNotNull();

        RulesEngineParameters rulesEngineParameters = rulesEngine.getParameters();
        assertThat(rulesEngineParameters.getName()).isEqualTo(NAME);
        assertThat(rulesEngineParameters.getPriorityThreshold()).isEqualTo(RULE_PRIORITY_THRESHOLD);
        assertThat(rulesEngineParameters.isSkipOnFirstAppliedRule()).isTrue();
        assertThat(rulesEngineParameters.isSkipOnFirstNonTriggeredRule()).isTrue();
        assertThat(rulesEngineParameters.isSkipOnFirstFailedRule()).isTrue();
        assertThat(rulesEngine.getRules()).isEqualTo(new HashSet<>(expectedRules));
        assertThat(rulesEngine.getRuleListeners()).isEqualTo(expectedRuleListeners);
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
