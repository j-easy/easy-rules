/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package net.benas.easyrules.core;

import net.benas.easyrules.api.ActionPerformer;
import net.benas.easyrules.api.ConditionTrigger;

/**
 * Class representing a rule than can be fired by the rules engine.<br/>
 *
 * Rules are registered in the rules engine registry and must have a <strong>unique</strong> name.<br/>
 *
 * Rules are fired according to their priority. By default, lower values represent higher priorities. To override
 * this default behavior, override {@link net.benas.easyrules.core.Rule#compareTo(net.benas.easyrules.core.Rule)} and
 * provide custom priority strategy.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class Rule implements Comparable<Rule> {

    /**
     * Rule name.
     */
    private String name;

    /**
     * Rule description.
     */
    private String description;

    /**
     * Rule priority.
     */
    private int priority;

    /**
     * The rule trigger.
     */
    private ConditionTrigger conditionTrigger;

    /**
     * The rule action performer.
     */
    private ActionPerformer actionPerformer;

    public Rule(String name, String description, int priority, ConditionTrigger conditionTrigger, ActionPerformer actionPerformer) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.conditionTrigger = conditionTrigger;
        this.actionPerformer = actionPerformer;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public ConditionTrigger getConditionTrigger() {
        return conditionTrigger;
    }

    public ActionPerformer getActionPerformer() {
        return actionPerformer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setConditionTrigger(ConditionTrigger conditionTrigger) {
        this.conditionTrigger = conditionTrigger;
    }

    public void setActionPerformer(ActionPerformer actionPerformer) {
        this.actionPerformer = actionPerformer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        return name.equals(rule.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Rule rule) {
        if (priority < rule.getPriority()) {
            return -1;
        } else if (priority == rule.getPriority()) {
            return 0;
        } else {
            return 1;
        }
    }

}
