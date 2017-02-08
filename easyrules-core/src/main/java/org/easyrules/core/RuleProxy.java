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
package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.util.Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class RuleProxy implements InvocationHandler {

    private Object target;

    private static RuleDefinitionValidator ruleDefinitionValidator = new RuleDefinitionValidator();

    public RuleProxy(final Object target) {
        this.target = target;
    }

    /**
     * Makes the rule object implement the {@link org.easyrules.api.Rule} interface.
     *
     * @param rule the annotated rule object.
     * @return a proxy that implements the {@link org.easyrules.api.Rule} interface.
     */
    public static org.easyrules.api.Rule asRule(final Object rule) {
        org.easyrules.api.Rule result;
        if (Utils.getInterfaces(rule).contains(org.easyrules.api.Rule.class)) {
            result = (org.easyrules.api.Rule) rule;
        } else {
            ruleDefinitionValidator.validateRuleDefinition(rule);
            result = (org.easyrules.api.Rule) Proxy.newProxyInstance(
                    org.easyrules.api.Rule.class.getClassLoader(),
                    new Class[]{org.easyrules.api.Rule.class, Comparable.class},
                    new RuleProxy(rule));
        }
        return result;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        String methodName = method.getName();
        if (methodName.equals("getName")) {
            return getRuleName();
        }
        if (methodName.equals("getDescription")) {
            return getRuleDescription();
        }
        if (methodName.equals("getPriority")) {
            return getRulePriority();
        }
        if (methodName.equals("evaluate")) {
            return getConditionMethod().invoke(target, args); // validated upfront
        }
        if (methodName.equals("execute")) {
            for (ActionMethodOrderBean actionMethodBean : getActionMethodBeans()) {
                actionMethodBean.getMethod().invoke(target);
            }
        }
        if (methodName.equals("equals")) {
            return target.equals(args[0]);
        }
        if (methodName.equals("hashCode")) {
            return target.hashCode();
        }
        if (methodName.equals("toString")) {
            return target.toString();
        }
        if (methodName.equals("compareTo")) {
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

    private int compareTo(final org.easyrules.api.Rule otherRule) throws Exception {
        String otherName = otherRule.getName();
        int otherPriority = otherRule.getPriority();
        String name = getRuleName();
        int priority = getRulePriority();

        if (priority < otherPriority) {
            return -1;
        } else if (priority > otherPriority) {
            return 1;
        } else {
            return name.compareTo(otherName);
        }
    }

    private int getRulePriority() throws Exception {
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
        Set<ActionMethodOrderBean> actionMethodBeans = new TreeSet<>();
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
        return getTargetClass().getMethods();
    }

    private Rule getRuleAnnotation() {
        return Utils.findAnnotation(Rule.class, getTargetClass());
    }

    private String getRuleName() {
        Rule rule = getRuleAnnotation();
        return rule.name().equals(Utils.DEFAULT_RULE_NAME) ? getTargetClass().getSimpleName() : rule.name();
    }

    private String getRuleDescription() {
        // Default description = "when " + conditionMethodName + " then " + comma separated actionMethodsNames
        StringBuilder description = new StringBuilder();
        appendConditionMethodName(description);
        appendActionMethodsNames(description);

        Rule rule = getRuleAnnotation();
        return rule.description().equals(Utils.DEFAULT_RULE_DESCRIPTION) ? description.toString() : rule.description();
    }

    private void appendConditionMethodName(StringBuilder description) {
        Method method = getConditionMethod();
        if (method != null) {
            description.append("when ");
            description.append(method.getName());
            description.append(" then ");
        }
    }

    private void appendActionMethodsNames(StringBuilder description) {
        Iterator<ActionMethodOrderBean> iterator = getActionMethodBeans().iterator();
        while (iterator.hasNext()) {
            description.append(iterator.next().getMethod().getName());
            if (iterator.hasNext()) {
                description.append(",");
            }
        }
    }

    private Class<?> getTargetClass() {
        return target.getClass();
    }

}
