package org.jeasy.rules.starter.support;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Indicate a rule group and Assign a name
 *
 * @author venus
 * @version 1
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RuleGroup {

    /**
     * bean bean alias
     * @return alias
     */
    String value() default "";

    /**
     * group name
     * @return name
     */
    String name();
}
