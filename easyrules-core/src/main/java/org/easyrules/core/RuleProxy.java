package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

class RuleProxy implements InvocationHandler {

    private Object target;

    private static RuleDefinitionValidator ruleDefinitionValidator = new RuleDefinitionValidator();

    public RuleProxy(Object target) {
        this.target = target;
    }

    /**
     * Makes the rule object implement the {@link org.easyrules.api.Rule} interface.
     *
     * @param rule the annotated rule object.
     * @return a proxy that implements the {@link org.easyrules.api.Rule} interface.
     */
    public static org.easyrules.api.Rule asRule(Object rule) {

        ruleDefinitionValidator.validateRuleDefinition(rule);

        return (org.easyrules.api.Rule) Proxy.newProxyInstance(
                org.easyrules.api.Rule.class.getClassLoader(),
                new Class[]{org.easyrules.api.Rule.class, Comparable.class},
                new RuleProxy(rule));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getName")) {
            return getRuleAnnotation().name();
        }
        if (method.getName().equals("getDescription")) {
            return getRuleAnnotation().description();
        }
        if (method.getName().equals("getPriority")) {
            return getPriority();
        }
        if (method.getName().equals("evaluate")) {
            return getConditionMethod().invoke(target, args); // validated upfront
        }
        if (method.getName().equals("execute")) {
            for (ActionMethodOrderBean actionMethodBean : getActionMethodBeans()) {
                actionMethodBean.getMethod().invoke(target);
            }
        }
        if (method.getName().equals("compareTo")) {
            Method compareToMethod = getCompareToMethod();
            if (compareToMethod != null) {
                return compareToMethod.invoke(target, args);
            } else {
                org.easyrules.api.Rule otherRule = (org.easyrules.api.Rule) args[0];
                return compareTo(otherRule);
            }
        }
        return null;
    }

    private int compareTo(org.easyrules.api.Rule otherRule) throws Exception {
        String otherName = otherRule.getName();
        int otherPriority = otherRule.getPriority();
        String name = getRuleAnnotation().name();
        int priority = getPriority();

        if (priority < otherPriority) {
            return -1;
        } else if (priority > otherPriority) {
            return 1;
        } else {
            return name.compareTo(otherName);
        }
    }

    private int getPriority() throws Exception {
        int priority = Utils.DEFAULT_RULE_PRIORITY;

        Method[] methods = getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Priority.class)) {
                priority = (Integer) method.invoke(target);
                break;
            }
        }
        return priority;
    }

    private Method getConditionMethod() {
        Method[] methods = getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Condition.class)) {
                return method;
            }
        }
        return null;
    }

    private Set<ActionMethodOrderBean> getActionMethodBeans() {
        Method[] methods = getMethods();
        Set<ActionMethodOrderBean> actionMethodBeans = new TreeSet<ActionMethodOrderBean>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Action.class)) {
                Action actionAnnotation = method.getAnnotation(Action.class);
                int order = actionAnnotation.order();
                actionMethodBeans.add(new ActionMethodOrderBean(method, order));
            }
        }
        return actionMethodBeans;
    }

    private Method getCompareToMethod() {
        Method[] methods = getMethods();
        for (Method method : methods) {
            if (method.getName().equals("compareTo")) {
                return method;
            }
        }
        return null;
    }

    private Method[] getMethods() {
        return target.getClass().getMethods();
    }

    private Rule getRuleAnnotation() {
        return target.getClass().getAnnotation(Rule.class);
    }

}