package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

import java.util.*;

public class MVELConditionalRuleGroup extends MVELCompositeRule {

    private Set<Rule> successfulEvaluations;
    private Rule conditionalRule;

    public MVELConditionalRuleGroup() { super(); }

    /**
     * Set rule name.
     *
     * @param name of the rule
     * @return this rule
     */
    public MVELConditionalRuleGroup name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Set rule description.
     *
     * @param description of the rule
     * @return this rule
     */
    public MVELConditionalRuleGroup description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Set rule priority.
     *
     * @param priority of the rule
     * @return this rule
     */
    public MVELConditionalRuleGroup priority(int priority) {
        this.priority = priority;
        return this;
    }


    @Override
    public boolean evaluate(Facts facts) {
        successfulEvaluations = new HashSet<>();
        conditionalRule = getRuleWithHighestPriority();
        if (conditionalRule.evaluate(facts)) {
            for (Rule rule : rules) {
                if (rule != conditionalRule && rule.evaluate(facts)) {
                    successfulEvaluations.add(rule);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void execute(Facts facts) throws Exception {
        conditionalRule.execute(facts);
        for (Rule rule : successfulEvaluations) {
            rule.execute(facts);
        }
    }

    private Rule getRuleWithHighestPriority() {
        List<Rule> copy = sortRules();
        // make sure that we only have one rule with the highest priority
        Rule highest = copy.get(0);
        if (copy.size() > 1 && copy.get(1).getPriority() == highest.getPriority()) {
            throw new IllegalArgumentException("Only one rule can have highest priority");
        }
        return highest;
    }

    private List<Rule> sortRules() {
        List<Rule> copy = new ArrayList<>(rules);
        Collections.sort(copy, new Comparator<Rule>() {
            @Override
            public int compare(Rule o1, Rule o2) {
                Integer i2 = o2.getPriority();
                return i2.compareTo(o1.getPriority());
            }
        });
        return copy;
    }
}
