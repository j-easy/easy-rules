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
package org.jeasy.rules.core;

import org.jeasy.rules.annotation.*;
import org.jeasy.rules.api.Facts;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * Validate that an annotated rule object is well defined.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
class RuleDefinitionValidator {

    void validateRuleDefinition(final Object rule) {
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
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule.getClass().getName(), Condition.class.getName()));
        }

        Method conditionMethod = conditionMethods.get(0);

        if (!isConditionMethodWellDefined(conditionMethod)) {
            throw new IllegalArgumentException(format("Condition method '%s' defined in rule '%s' must be public, may have parameters annotated with @Fact (and/or a parameter of type Facts) and return boolean type.", conditionMethod, rule.getClass().getName()));
        }
    }

    private void checkActionMethods(final Object rule) {
        List<Method> actionMethods = getMethodsAnnotatedWith(Action.class, rule);
        if (actionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' must have at least one public method annotated with '%s'", rule.getClass().getName(), Action.class.getName()));
        }

        for (Method actionMethod : actionMethods) {
            if (!isActionMethodWellDefined(actionMethod)) {
                throw new IllegalArgumentException(format("Action method '%s' defined in rule '%s' must be public and may have parameters annotated with @Fact (and/or a parameter of type Facts).", actionMethod, rule.getClass().getName()));
            }
        }
    }

    private void checkPriorityMethod(final Object rule) {

        List<Method> priorityMethods = getMethodsAnnotatedWith(Priority.class, rule);

        if (priorityMethods.isEmpty()) {
            return;
        }

        if (priorityMethods.size() > 1) {
            throw new IllegalArgumentException(format("Rule '%s' must have exactly one method annotated with '%s'", rule.getClass().getName(), Priority.class.getName()));
        }

        Method priorityMethod = priorityMethods.get(0);

        if (!isPriorityMethodWellDefined(priorityMethod)) {
            throw new IllegalArgumentException(format("Priority method '%s' defined in rule '%s' must be public, have no parameters and return integer type.", priorityMethod, rule.getClass().getName()));
        }
    }

    private boolean isRuleClassWellDefined(final Object rule) {
        return Utils.isAnnotationPresent(Rule.class, rule.getClass());
    }

    private boolean isConditionMethodWellDefined(final Method method) {
        Parameter[] parameters = method.getParameters();
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Boolean.TYPE)
                && validParameters(parameters);
    }

    private boolean validParameters(Parameter[] parameters) {
        List<Parameter> notAnnotatedParams = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (parameter.getAnnotation(Fact.class) == null) {
                notAnnotatedParams.add(parameter);
            }
        }
        if (notAnnotatedParams.size() > 1) {
            return false;
        } else if (notAnnotatedParams.size() == 1) {
            Parameter parameter = notAnnotatedParams.get(0);
            return parameter.getType().isAssignableFrom(Facts.class);
        }
        return true;
    }

    private boolean isActionMethodWellDefined(final Method method) {
        Parameter[] parameters = method.getParameters();
        return Modifier.isPublic(method.getModifiers())
                && validParameters(parameters);
    }

    private boolean isPriorityMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Integer.TYPE)
                && method.getParameterTypes().length == 0;
    }

    private List<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> annotation, final Object rule) {
        Method[] methods = getMethods(rule);
        List<Method> annotatedMethods = new ArrayList<>();
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
