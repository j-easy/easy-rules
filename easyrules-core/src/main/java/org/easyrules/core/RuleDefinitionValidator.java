package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.util.EasyRulesConstants;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Validate that an annotated rule object is well defined.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
class RuleDefinitionValidator {

    public void validateRuleDefinition(Object rule) {
        checkRuleClass(rule);
        checkConditionMethod(rule);
        checkActionMethods(rule);
        checkPriorityMethod(rule);
    }

    private void checkRuleClass(Object rule) {
        if (!isRuleClassWellDefined(rule)) {
            throw new IllegalArgumentException(format("Rule '%s' is not annotated with '%s'", rule, Rule.class.getClass()));
        }
    }

    private void checkConditionMethod(Object rule) {
        List<Method> conditionMethods = getMethodsAnnotatedWith(Condition.class, rule);
        if (conditionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' does not have a public method annotated with '%s'", rule, Condition.class.getClass()));
        }

        if(conditionMethods.size() > 1) {
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with annotated with '%s'", rule, Condition.class.getClass()));
        }

        Method conditionMethod = conditionMethods.get(0);

        if (!isConditionMethodWellDefined(conditionMethod)) {
            throw new IllegalArgumentException(format("Condition method '%s' defined in rule '%s' must be public, have no parameters and return boolean type.", conditionMethod, rule));
        }
    }

    private void checkActionMethods(Object rule) {
        List<Method> actionMethods = getMethodsAnnotatedWith(Action.class, rule);
        if (actionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' does not have a public method annotated with '%s'", rule, Action.class.getClass()));
        }

        for (Method actionMethod : actionMethods) {
            if (!isActionMethodWellDefined(actionMethod)) {
                throw new IllegalArgumentException(format("Action method '%s' defined in rule '%s' must be public and have no parameters.", actionMethod, rule));
            }
        }
    }

    private int checkPriorityMethod(Object rule) {

        List<Method> priorityMethods = getMethodsAnnotatedWith(Priority.class, rule);

        if (priorityMethods.isEmpty()) {
            return EasyRulesConstants.DEFAULT_RULE_PRIORITY;
        }

        if (priorityMethods.size() > 1) {
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule, Priority.class.getClass()));
        }

        Method priorityMethod = priorityMethods.get(0);

        if (!isPriorityMethodWellDefined(priorityMethod)) {
            throw new IllegalArgumentException(format("Priority method '%s' defined in rule '%s' must be public, have no parameters and return integer type.", priorityMethod, rule));
        }

        try {
            return (Integer) priorityMethod.invoke(rule);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(format("Unable to access method '%s' to get priority of rule '%s'", priorityMethod, rule), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(format("Unable to invoke method '%s' to get priority of rule '%s'", priorityMethod, rule), e);
        }
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
        return Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0;
    }

    private boolean isPriorityMethodWellDefined(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Integer.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private List<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation, Object rule) {
        Method[] methods = getMethods(rule);
        List<Method> annotatedMethods = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private Method[] getMethods(Object rule) {
        return rule.getClass().getMethods();
    }

}
