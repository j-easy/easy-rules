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
package org.easyrules.spring.boot.autoconfigure;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.core.BasicRule;
import org.easyrules.spring.RulesEngineFactoryBean;
import org.easyrules.spring.SpringRule;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link EasyRulesAutoConfiguration}
 *
 * @author Kyle Anderson
 */
public class EasyRulesAutoConfigurationTest {

    private AnnotationConfigApplicationContext context;

    @After
    public void tearDown() {
        if(this.context != null) {
            this.context.close();
        }
    }

    @Test
    public void rulesEngineFactoryBeanTest() {
        load(EmptyConfiguration.class);
        RulesEngineFactoryBean rulesEngineFactoryBean = this.context.getBean(RulesEngineFactoryBean.class);
        assertNotNull(rulesEngineFactoryBean);
    }

    @Test
    public void rulesEngineBeanTest() {
        load(EmptyConfiguration.class);
        RulesEngine rulesEngine = this.context.getBean(RulesEngine.class);
        assertNotNull(rulesEngine);
    }

    @Test
    public void noRulesConfigured() {
        load(EmptyConfiguration.class);
        RulesEngine rulesEngine = this.context.getBean(RulesEngine.class);
        assertEquals(0, rulesEngine.getRules().size());
    }

    @Test
    public void ruleImplementationBeanTest() {
        load(RuleImplementationBeanConfiguration.class);
        RulesEngine rulesEngine = this.context.getBean(RulesEngine.class);
        assertEquals(1, rulesEngine.getRules().size());
    }

    @Test
    public void ruleComponentAnnotationTest() {
        load(RuleComponentAnnotationConfiguration.class);
        RulesEngine rulesEngine = this.context.getBean(RulesEngine.class);
        assertEquals(1, rulesEngine.getRules().size());
    }

    @Test
    public void springRuleAnnotationTest() {
        load(SpringRuleAnnotationConfiguration.class);
        RulesEngine rulesEngine = this.context.getBean(RulesEngine.class);
        assertEquals(1, rulesEngine.getRules().size());
    }

    @Configuration
    static class EmptyConfiguration {}

    @Configuration
    static class RuleImplementationBeanConfiguration {
        @Bean
        public RuleImplementationBean ruleImplementationBean() {
            return new RuleImplementationBean();
        }

        static class RuleImplementationBean extends BasicRule {
            @Override
            public boolean evaluate() {
                return true;
            }

            @Override
            public void execute() throws Exception {}
        }

    }

    @Configuration
    static class RuleComponentAnnotationConfiguration {
        @Component
        @Rule
        static class RuleComponentAnnotation {
            @Condition
            public boolean when() {
                return true;
            }

            @Action
            public void then() {}
        }
    }

    @Configuration
    static class SpringRuleAnnotationConfiguration {
        @SpringRule
        static class SpringRuleAnnotation {
            @Condition
            public boolean when() {
                return true;
            }

            @Action
            public void then() {}
        }
    }

    private void load(Class<?> config, String... environment) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        EnvironmentTestUtils.addEnvironment(applicationContext, environment);
        applicationContext.register(config);
        applicationContext.register(EasyRulesAutoConfiguration.class);
        applicationContext.refresh();
        this.context = applicationContext;
    }
}
