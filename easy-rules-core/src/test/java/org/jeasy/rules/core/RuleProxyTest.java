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
package org.jeasy.rules.core;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.AnnotatedRuleWithMetaRuleAnnotation;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.api.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class RuleProxyTest {

    @Test
    public void proxyingHappensEvenWhenRuleIsAnnotatedWithMetaRuleAnnotation() {
        // Given
        AnnotatedRuleWithMetaRuleAnnotation rule = new AnnotatedRuleWithMetaRuleAnnotation();

        // When
        Rule proxy = RuleProxy.asRule(rule);

        // Then
        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatImplementsRule() {
        Object rule = new BasicRule();
        Rule proxy = RuleProxy.asRule(rule);

        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatExtendsBasicRule() {
        Object rule = new CompositeRule();
        Rule proxy = RuleProxy.asRule(rule);

        assertNotNull(proxy.getDescription());
        assertNotNull(proxy.getName());
    }

    @Test
    public void asRuleForObjectThatHasProxied() {
        Object rule = new DummyRule();
        Rule proxy1 = RuleProxy.asRule(rule);
        Rule proxy2 = RuleProxy.asRule(proxy1);

        assertEquals(proxy1.getDescription(), proxy2.getDescription());
        assertEquals(proxy1.getName(), proxy2.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void asRuleForPojo() {
        Object rule = new Object();
        Rule proxy = RuleProxy.asRule(rule);
    }

    @Test
    public void invokeEquals() {

        Object rule = new DummyRule();
        Rule proxy1 = RuleProxy.asRule(rule);
        Rule proxy2 = RuleProxy.asRule(proxy1);
        Rule proxy3 = RuleProxy.asRule(proxy2);
        /**
         * @see Object#equals(Object) reflexive
         */
        assertEquals(rule,rule);
        assertEquals(proxy1, proxy1);
        assertEquals(proxy2, proxy2);
        assertEquals(proxy3, proxy3);
        /**
         * @see Object#equals(Object) symmetric
         */
        assertNotEquals(rule, proxy1);
        assertNotEquals(proxy1, rule);
        assertEquals(proxy1, proxy2);
        assertEquals(proxy2, proxy1);
        /**
         * @see Object#equals(Object) transitive consistent
         */
        assertEquals(proxy1, proxy2);
        assertEquals(proxy2, proxy3);
        assertEquals(proxy3, proxy1);
        /**
         * @see Object#equals(Object) non-null
         */
        assertNotEquals(rule, null);
        assertNotEquals(proxy1, null);
        assertNotEquals(proxy2, null);
        assertNotEquals(proxy3, null);
    }
    
    
    @Test
    public void invokeHashCode() {

        Object rule = new DummyRule();
        Rule proxy1 = RuleProxy.asRule(rule);
        Rule proxy2 = RuleProxy.asRule(proxy1);
        /**
         * @see Object#hashCode rule1
         */
        assertEquals(proxy1.hashCode(), proxy1.hashCode());
        /**
         * @see Object#hashCode rule2
         */
        assertEquals(proxy1, proxy2);
        assertEquals(proxy1.hashCode(), proxy2.hashCode());
        /**
         * @see Object#hashCode rule3
         */
        assertNotEquals(rule, proxy1);
        assertNotEquals(rule.hashCode(), proxy1.hashCode());
    }
    
    @Test
    public void invokeToString() {

        Object rule = new DummyRule();
        Rule proxy1 = RuleProxy.asRule(rule);
        Rule proxy2 = RuleProxy.asRule(proxy1);
        
        assertEquals(proxy1.toString(), proxy1.toString());
                
        assertEquals(proxy1.toString(), proxy2.toString());
        
        assertEquals(rule.toString(), proxy1.toString());
    }

    @Test
    public void testPriorityFromAnnotation() {

        @org.jeasy.rules.annotation.Rule(priority = 1)
        class MyRule {
            @Condition
            public boolean when() { return true; }

            @Action
            public void then() { }
        }

        Object rule = new MyRule();
        Rule proxy = RuleProxy.asRule(rule);
        assertEquals(1, proxy.getPriority());
    }

    @Test
    public void testPriorityFromMethod() {

        @org.jeasy.rules.annotation.Rule
        class MyRule {
            @Condition
            public boolean when() { return true; }

            @Action
            public void then() { }

            @Priority
            public int getPriority() { return 2; }
        }

        Object rule = new MyRule();
        Rule proxy = RuleProxy.asRule(rule);
        assertEquals(2, proxy.getPriority());
    }

    @Test
    public void testPriorityPrecedence() {

        @org.jeasy.rules.annotation.Rule(priority = 1)
        class MyRule {
            @Condition
            public boolean when() { return true; }

            @Action
            public void then() { }

            @Priority
            public int getPriority() { return 2; }
        }

        Object rule = new MyRule();
        Rule proxy = RuleProxy.asRule(rule);
        assertEquals(2, proxy.getPriority());
    }

    @Test
    public void testDefaultPriority() {

        @org.jeasy.rules.annotation.Rule
        class MyRule {
            @Condition
            public boolean when() { return true; }

            @Action
            public void then() { }
        }

        Object rule = new MyRule();
        Rule proxy = RuleProxy.asRule(rule);
        assertEquals(Rule.DEFAULT_PRIORITY, proxy.getPriority());
    }

    @org.jeasy.rules.annotation.Rule
    class DummyRule {
        @Condition
        public boolean when() { return true; }

        @Action
        public void then() { }
        
        @Override
        public String toString() {
        	return "I am a Dummy rule";
        }
        
    }

}
