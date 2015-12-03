package org.easyrules.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;

/**
 * Utilities class.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public final class Utils {

    /**
     * Default rule name.
     */
    public static final String DEFAULT_RULE_NAME = "rule";

    /**
     * Default engine name.
     */
    public static final String DEFAULT_ENGINE_NAME = "engine";

    /**
     * Default rule description.
     */
    public static final String DEFAULT_RULE_DESCRIPTION = "description";

    /**
     * Default rule priority.
     */
    public static final int DEFAULT_RULE_PRIORITY = Integer.MAX_VALUE - 1;

    /**
     * Default rule priority threshold.
     */
    public static final int DEFAULT_RULE_PRIORITY_THRESHOLD = Integer.MAX_VALUE;

    private Utils() {

    }

    public static void muteLoggers() {
        Enumeration<String> loggerNames = LogManager.getLogManager().getLoggerNames();
        while (loggerNames.hasMoreElements()) {
            String loggerName = loggerNames.nextElement();
            if (loggerName.startsWith("org.easyrules")) {
                muteLogger(loggerName);
            }
        }
    }

    private static void muteLogger(final String logger) {
        Logger.getLogger(logger).setUseParentHandlers(false);
        Handler[] handlers = Logger.getLogger(logger).getHandlers();
        for (Handler handler : handlers) {
            Logger.getLogger(logger).removeHandler(handler);
        }
    }

    public static List<Class> getInterfaces(final Object rule) {
        List<Class> interfaces = new ArrayList<Class>();
        Class clazz = rule.getClass();
        while (clazz.getSuperclass() != null) {
            interfaces.addAll(asList(clazz.getInterfaces()));
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A findAnnotation(
            final Class<? extends Annotation> targetAnnotation, final Class annotatedType) {

        checkNotNull(targetAnnotation, "targetAnnotation");
        checkNotNull(annotatedType, "annotatedType");

        Annotation foundAnnotation = annotatedType.getAnnotation(targetAnnotation);
        if (foundAnnotation == null) {
            for (Annotation annotation : annotatedType.getAnnotations()) {
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(targetAnnotation)) {
                    foundAnnotation = annotationType.getAnnotation(targetAnnotation);
                    break;
                }
            }
        }
        return (A) foundAnnotation;
    }

    public static boolean isAnnotationPresent(
            final Class<? extends Annotation> targetAnnotation, final Class annotatedType) {

        return findAnnotation(targetAnnotation, annotatedType) != null;
    }

    public static void checkNotNull(final Object argument, final String argumentName) {
        if (argument == null) {
            throw new IllegalArgumentException(format("The %s must not be null", argumentName));
        }
    }

}
