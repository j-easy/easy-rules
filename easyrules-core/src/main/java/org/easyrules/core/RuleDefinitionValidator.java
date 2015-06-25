package org.easyrules.core;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;
import org.easyrules.util.Utils;

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

    public void validateRuleDefinition(final Object rule) {
        checkRuleClass(rule);
        checkConditionMethod(rule);
        checkActionMethods(rule);
        checkPriorityMethod(rule);
    }

    private void checkRuleClass(final Object rule) {
        if (!isRuleClassWellDefined(rule)) {
            throw new IllegalArgumentException(format("Rule '%s' is not annotated with '%s'", rule.getClass().getName(), Rule.class.getName()));
        }
    }

    private void checkConditionMethod(final Object rule) {
        List<Method> conditionMethods = getMethodsAnnotatedWith(Condition.class, rule);
        if (conditionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' must have a public method annotated with '%s'", rule.getClass().getName(), Condition.class.getName()));
        }

        if (conditionMethods.size() > 1) {
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with annotated with '%s'", rule.getClass().getName(), Condition.class.getName()));
        }

        Method conditionMethod = conditionMethods.get(0);

        if (!isConditionMethodWellDefined(conditionMethod)) {
            throw new IllegalArgumentException(format("Condition method '%s' defined in rule '%s' must be public, have no parameters and return boolean type.", conditionMethod, rule.getClass().getName()));
        }
    }

    private void checkActionMethods(final Object rule) {
        List<Method> actionMethods = getMethodsAnnotatedWith(Action.class, rule);
        if (actionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' must have a public method annotated with '%s'", rule.getClass().getName(), Action.class.getName()));
        }

        for (Method actionMethod : actionMethods) {
            if (!isActionMethodWellDefined(actionMethod)) {
                throw new IllegalArgumentException(format("Action method '%s' defined in rule '%s' must be public and have no parameters.", actionMethod, rule.getClass().getName()));
            }
        }
    }

    private int checkPriorityMethod(final Object rule) {

        List<Method> priorityMethods = getMethodsAnnotatedWith(Priority.class, rule);

        if (priorityMethods.isEmpty()) {
            return Utils.DEFAULT_RULE_PRIORITY;
        }

        if (priorityMethods.size() > 1) {
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule.getClass().getName(), Priority.class.getName()));
        }

        Method priorityMethod = priorityMethods.get(0);

        if (!isPriorityMethodWellDefined(priorityMethod)) {
            throw new IllegalArgumentException(format("Priority method '%s' defined in rule '%s' must be public, have no parameters and return integer type.", priorityMethod, rule.getClass().getName()));
        }

        try {
            return (Integer) priorityMethod.invoke(rule);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(format("Unable to access method '%s' to get priority of rule '%s'", priorityMethod, rule.getClass().getName()), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(format("Unable to invoke method '%s' to get priority of rule '%s'", priorityMethod, rule.getClass().getName()), e);
        }
    }

    private boolean isRuleClassWellDefined(final Object rule) {
        return rule.getClass().isAnnotationPresent(Rule.class);
    }

    private boolean isConditionMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Boolean.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private boolean isActionMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0;
    }

    private boolean isPriorityMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Integer.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private List<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> annotation, final Object rule) {
        Method[] methods = getMethods(rule);
        List<Method> annotatedMethods = new ArrayList<Method>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method);
            }
        }
        return annotatedMethods;
    }

    private Method[] getMethods(final Object rule) {
        return rule.getClass().getMethods();
    }

}
