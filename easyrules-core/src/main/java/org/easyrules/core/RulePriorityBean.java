package org.easyrules.core;

/**
 * Utility class that associates a rule to its priority.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
class RulePriorityBean implements Comparable<RulePriorityBean> {

    private int priority;

    private Object rule;

    private RulePriorityBean(int priority, Object rule) {
        this.priority = priority;
        this.rule = rule;
    }

    public int getPriority() {
        return priority;
    }

    public Object getRule() {
        return rule;
    }

    @Override
    public int compareTo(RulePriorityBean ruleBean) {
        return new Integer(priority).compareTo(ruleBean.getPriority());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RulePriorityBean)) return false;

        RulePriorityBean ruleBean = (RulePriorityBean) o;

        if (priority != ruleBean.priority) return false;
        if (!rule.equals(ruleBean.rule)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = priority;
        result = 31 * result + rule.hashCode();
        return result;
    }

}