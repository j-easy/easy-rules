package org.easyrules.annotation;

@MetaRule
public class AnnotatedRuleWithMetaRuleAnnotation {

    @Condition
    public boolean when() {
        return true;
    }

    @Action
    public void then() throws Exception {
    }
}
