package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Rule;

import java.util.List;

class MVELRuleDefinition {

    private String name = Rule.DEFAULT_NAME;
    private String description = Rule.DEFAULT_DESCRIPTION;
    private int priority = Rule.DEFAULT_PRIORITY;
    private String condition;
    private List<String> actions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    Rule create() {
        MVELRule mvelRule = new MVELRule(name, description, priority);
        mvelRule.when(condition);
        for (String action : actions) {
            mvelRule.then(action);
        }
        return mvelRule;
    }
}