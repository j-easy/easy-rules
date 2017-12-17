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

import static java.lang.String.format;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

/**
 * This component validates that an annotated rule object is well defined.
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
            throw new IllegalArgumentException(format("Condition method '%s' defined in rule '%s' must be public, may have parameters annotated with @Fact (and/or exactly one parameter of type or extending Facts) and return boolean type.", conditionMethod, rule.getClass().getName()));
        }
    }

    private void checkActionMethods(final Object rule) {
        List<Method> actionMethods = getMethodsAnnotatedWith(Action.class, rule);
        if (actionMethods.isEmpty()) {
            throw new IllegalArgumentException(format("Rule '%s' must have at least one public method annotated with '%s'", rule.getClass().getName(), Action.class.getName()));
        }

        for (Method actionMethod : actionMethods) {
            if (!isActionMethodWellDefined(actionMethod)) {
                throw new IllegalArgumentException(format("Action method '%s' defined in rule '%s' must be public, must return void type and may have parameters annotated with @Fact (and/or exactly one parameter of type or extending Facts).", actionMethod, rule.getClass().getName()));
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
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Boolean.TYPE)
                && validParameters(method);
    }

    private boolean validParameters(final Method method) {
        int notAnnotatedParameterCount = 0;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for(Annotation[] anns : parameterAnnotations){
            if(anns.length == 0){
                notAnnotatedParameterCount += 1;
            } else {
                //Annotation types has to be Fact
                for(Annotation ann : anns){
                    if(!ann.annotationType().equals(Fact.class)){
                        return false;
                    }
                }
            }
        }
        if(notAnnotatedParameterCount > 1){
            return false;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        if(parameterTypes.length == 1 && notAnnotatedParameterCount == 1){
            return Facts.class.isAssignableFrom(parameterTypes[0]);
        }
        return true;
    }

    private boolean isActionMethodWellDefined(final Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(Void.TYPE)
                && validParameters(method);
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
