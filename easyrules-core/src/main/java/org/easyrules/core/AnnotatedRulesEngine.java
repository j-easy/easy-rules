/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.util.EasyRulesConstants;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Annotated rules engine implementation.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class AnnotatedRulesEngine extends AbstractRulesEngine<Object> {

    private static final Logger LOGGER = Logger.getLogger(AnnotatedRulesEngine.class.getName());

    private List<RuleBean> ruleBeans;

    /**
     * Constructs an annotated rules engine with default values.
     */
    public AnnotatedRulesEngine() {
        this(false, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs an annotated rules engine.
     *
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     */
    public AnnotatedRulesEngine(boolean skipOnFirstAppliedRule) {
        this(skipOnFirstAppliedRule, EasyRulesConstants.DEFAULT_RULE_PRIORITY_THRESHOLD);
    }

    /**
     * Constructs an annotated rules engine.
     *
     * @param rulePriorityThreshold rule priority threshold
     */
    public AnnotatedRulesEngine(int rulePriorityThreshold) {
        this(false, rulePriorityThreshold);
    }

    /**
     * Constructs an annotated rules engine.
     *
     * @param skipOnFirstAppliedRule true if the engine should skip next rule after the first applied rule
     * @param rulePriorityThreshold  rule priority threshold
     */
    public AnnotatedRulesEngine(boolean skipOnFirstAppliedRule, int rulePriorityThreshold) {
        rules = new TreeSet<Object>();
        ruleBeans = new ArrayList<RuleBean>();
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        this.rulePriorityThreshold = rulePriorityThreshold;
    }

    @Override
    public void registerRule(Object rule) {

        checkRuleClass(rule);
        checkConditionMethod(rule);
        checkActionMethods(rule);

        int priority = getPriority(rule);

        ruleBeans.add(new RuleBean(priority, rule));
    }

    @Override
    public void unregisterRule(Object rule) {
        try {
            //rule priority method has been checked at registration time
            int priority = (Integer) getPriorityMethods(rule).get(0).invoke(rule);
            ruleBeans.remove(new RuleBean(priority, rule));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, String.format("Unable to unregister rule '%s'", rule), e);
        }

    }

    @Override
    public void fireRules() {

        if (ruleBeans.isEmpty()) {
            LOGGER.warning("No rules registered! Nothing to apply.");
            return;
        }

        logEngineParameters();

        //sort rules according to their priorities
        Collections.sort(ruleBeans);

        for (RuleBean ruleBean : ruleBeans) {

            Object rule = ruleBean.getRule();

            Rule annotation = rule.getClass().getAnnotation(Rule.class);
            String name = annotation.name();

            Method conditionMethod = getConditionMethod(rule);
            List<ActionMethodBean> actionMethods = getActionMethodBeans(rule);

            //sort actions according to their execution order
            Collections.sort(actionMethods);

            try {

                int priority = ruleBean.getPriority();
                if (priority > rulePriorityThreshold) {
                    LOGGER.log(Level.INFO, "Rule priority threshold {0} exceeded at rule ''{1}'' (priority={2}), next applicable rules will be skipped.",
                            new Object[]{rulePriorityThreshold, name, priority});
                    break;
                }

                Boolean shouldApplyRule = (Boolean) conditionMethod.invoke(rule);

                if (shouldApplyRule) {
                    LOGGER.log(Level.INFO, "Rule ''{0}'' triggered.", name);
                    try {
                        //execute all actions in the defined order
                        for (ActionMethodBean actionMethodBean : actionMethods) {
                            actionMethodBean.getMethod().invoke(rule);
                        }
                        LOGGER.log(Level.INFO, "Rule ''{0}'' performed successfully.", name);

                        if (skipOnFirstAppliedRule) {
                            LOGGER.info("Next rules will be skipped according to parameter skipOnFirstAppliedRule.");
                            break;
                        }

                    } catch (Exception exception) {
                        LOGGER.log(Level.SEVERE, String.format("Rule '%s' performed with error.", name), exception);
                    }
                }
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, String.format("Unable to access method on rule '%s'", rule), e);
            } catch (InvocationTargetException e) {
                LOGGER.log(Level.SEVERE, String.format("Unable to invoke method on rule '%s'", rule), e);
            }

        }

    }

    @Override
    public void clearRules() {
        ruleBeans.clear();
        super.clearRules();
    }

    /*
     * Private Utility methods
     */

    private void checkRuleClass(Object rule) {
        //check if the rule class is annotated with @Rule
        if (!isRuleClassWellDefined(rule)) {
            throw new IllegalArgumentException(String.format("Rule '%s' is not annotated with '%s'", rule, Rule.class.getClass()));
        }
    }

    private void checkConditionMethod(Object rule) {
        //check if condition method is well defined
        Method conditionMethod = getConditionMethod(rule);
        if (null == conditionMethod) {
            throw new IllegalArgumentException(String.format("Rule '%s' does not have a public method annotated with '%s'", rule, Condition.class.getClass()));
        }

        if (!isConditionMethodWellDefined(conditionMethod)) {
            throw new IllegalArgumentException(String.format("Condition method '%s' defined in rule '%s' must be public, have no parameters and return boolean type.", conditionMethod, rule));
        }
    }

    private void checkActionMethods(Object rule) {
        //check if action methods are well defined
        List<ActionMethodBean> actionMethods = getActionMethodBeans(rule);
        if (actionMethods.isEmpty()) {
            throw new IllegalArgumentException(String.format("Rule '%s' does not have a public method annotated with '%s'", rule, Action.class.getClass()));
        }

        for (ActionMethodBean actionMethodBean : actionMethods) {
            Method actionMethod = actionMethodBean.getMethod();
            if (!isActionMethodWellDefined(actionMethod)) {
                throw new IllegalArgumentException(String.format("Action method '%s' defined in rule '%s' must be public and have no parameters.", actionMethod, rule));
            }
        }
    }

    private int getPriority(Object rule) {

        int priority = EasyRulesConstants.DEFAULT_RULE_PRIORITY;

        List<Method> priorityMethods = getPriorityMethods(rule);

        checkRulePriority(rule, priorityMethods);

        //exactly one method annotated with @Priority
        if (!priorityMethods.isEmpty()) {
            Method priorityMethod = priorityMethods.get(0);
            if (!isPriorityMethodWellDefined(priorityMethod)) {
                throw new IllegalArgumentException(String.format("Priority method '%s' defined in rule '%s' must be public, have no parameters and return integer type.", priorityMethod, rule));
            }
            try {
                priority = (Integer) priorityMethod.invoke(rule);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(String.format("Unable to access method '%s' to get priority of rule '%s'", priorityMethod, rule), e);
            } catch (InvocationTargetException e) {
                throw new IllegalArgumentException(String.format("Unable to invoke method '%s' to get priority of rule '%s'", priorityMethod, rule), e);
            }
        }
        return priority;
    }

    private void checkRulePriority(Object rule, List<Method> priorityMethods) {
        // more than one method annotated with @Priority
        if (priorityMethods.size() > 1) {
            throw new IllegalArgumentException(String.format("Rule '%s' has more than one method annotated with '%s'", rule, Priority.class.getClass()));
        }
    }

    private Method getConditionMethod(Object rule) {
        Method[] methods = rule.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Condition.class)) {
                return method;
            }
        }
        return null;
    }

    private List<ActionMethodBean> getActionMethodBeans(Object rule) {
        Method[] methods = rule.getClass().getMethods();
        List<ActionMethodBean> actionMethodBeans = new ArrayList<ActionMethodBean>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Action.class)) {
                Action actionAnnotation = method.getAnnotation(Action.class);
                int order = actionAnnotation.order();
                actionMethodBeans.add(new ActionMethodBean(method, order));
            }
        }
        return actionMethodBeans;
    }

    private List<Method> getPriorityMethods(Object rule) {
        Method[] methods = rule.getClass().getMethods();
        List<Method> priorityMethods = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Priority.class)) {
                priorityMethods.add(method);
            }
        }
        return priorityMethods;
    }

    private boolean isRuleClassWellDefined(Object rule) {
        return rule.getClass().isAnnotationPresent(Rule.class);
    }

    private boolean isConditionMethodWellDefined(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Boolean.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private boolean isActionMethodWellDefined(Method method) {
        return Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0;
    }

    private boolean isPriorityMethodWellDefined(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Integer.TYPE)
                && method.getParameterTypes().length == 0;
    }

    /**
     * Private utility class that associates an action method and its execution order.
     */
    private final class ActionMethodBean implements Comparable<ActionMethodBean> {

        private Method method;

        private int order;

        ActionMethodBean(Method method, int order) {
            this.method = method;
            this.order = order;
        }

        public int getOrder() {
            return order;
        }

        public Method getMethod() {
            return method;
        }

        @Override
        public int compareTo(ActionMethodBean actionMethodBean) {
            if (order < actionMethodBean.getOrder()) {
                return -1;
            } else if (order == actionMethodBean.getOrder()) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ActionMethodBean)) return false;

            ActionMethodBean that = (ActionMethodBean) o;

            if (order != that.order) return false;
            if (!method.equals(that.method)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = method.hashCode();
            result = 31 * result + order;
            return result;
        }

    }

    /**
     * Private utility class that associates a rule to its priority.
     */
    private final class RuleBean implements Comparable<RuleBean> {

        private int priority;

        private Object rule;

        private RuleBean(int priority, Object rule) {
            this.priority = priority;
            this.rule = rule;
        }

        private int getPriority() {
            return priority;
        }

        private Object getRule() {
            return rule;
        }

        @Override
        public int compareTo(RuleBean ruleBean) {
            if (priority < ruleBean.getPriority()) {
                return -1;
            } else if (priority == ruleBean.getPriority()) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof RuleBean)) return false;

            RuleBean ruleBean = (RuleBean) o;

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

}
