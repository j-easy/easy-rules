package org.easyrules.spring;

import org.easyrules.annotation.Rule;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that turns a POJO into:
 *
 * <ul>
 *     <li>an Easy Rules rule</li>
 *     <li>a Spring prototype-scoped bean</li>
 * </ul>
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Rule
@Component
@Scope("prototype")
public @interface SpringRule {
}
