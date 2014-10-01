package org.easyrules.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark the method to execute to get rule priority.
 * Must annotate any public method with no arguments and that returns an integer value.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Priority {

}
