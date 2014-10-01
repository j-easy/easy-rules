package org.easyrules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a method as a rule action.
 * Must annotate any public method with no arguments.
 * The method return value will be ignored by the engine.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {

    /**
     * The order in which the action should be executed.
     * @return he order in which the action should be executed
     */
    int order() default 0;

}
