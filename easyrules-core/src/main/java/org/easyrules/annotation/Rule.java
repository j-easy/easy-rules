package org.easyrules.annotation;

import org.easyrules.util.EasyRulesConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark a class as a rule.
 *
 * @author Mahmoud Ben Hassine (md.benhassine@gmail.com)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Rule {

    /**
     * The rule name which must be unique within an rules registry.
     * @return The rule name
     */
    public String name() default EasyRulesConstants.DEFAULT_RULE_NAME;

    /**
     * The rule description.
     * @return The rule description
     */
    public String description() default  EasyRulesConstants.DEFAULT_RULE_DESCRIPTION;

}
