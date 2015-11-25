package org.easyrules.core;

import org.easyrules.annotation.AnnotatedRuleWithMetaRuleAnnotation;
import org.easyrules.api.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RuleProxyTest {

    @Test
    public void proxyingHappensEvenWhenRuleIsAnnotatedWithMetaRuleAnnotation() {
        AnnotatedRuleWithMetaRuleAnnotation rule1 = new AnnotatedRuleWithMetaRuleAnnotation();

        Rule rule = RuleProxy.asRule(rule1);

        assertNotNull(rule.getDescription());
        assertNotNull(rule.getName());
    }
}
