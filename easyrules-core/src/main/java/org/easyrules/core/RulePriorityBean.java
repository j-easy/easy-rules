package org.easyrules.core;

/**
 * Utility class that associates a rule to its priority.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
final class RulePriorityBean implements Comparable<RulePriorityBean> {

    private int priority;

    private Object rule;

    private RulePriorityBean(final int priority, final Object rule) {
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
    public int compareTo(final RulePriorityBean ruleBean) {
        if (priority < ruleBean.getPriority()) {
            return -1;
        } else if (priority > ruleBean.getPriority()) {
            return 1;
        } else {
            return rule.equals(ruleBean.getRule()) ? 0 : 1;
        }
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
