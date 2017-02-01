package org.easyrules.core;

import org.easyrules.api.RuleListener;
import org.easyrules.api.RulesEngine;
import org.easyrules.util.Utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for rules engine instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineBuilder<E extends RulesEngine> {

    public static class EngineInstantiationException extends RuntimeException {
        private static final long serialVersionUID = -7927944703782618629L;

        /**
         * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
         *
         * @param message
         *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
         */
        public EngineInstantiationException(String message) {
            super(message);
        }

        /**
         * Constructs a new exception with the specified detail message and cause.
         * <p>
         * Note that the detail message associated with {@code cause} is <i>not</i> automatically incorporated in this exception's detail message.
         *
         * @param message
         *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
         * @param cause
         *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and indicates that the cause is
         *            nonexistent or unknown.)
         */
        public EngineInstantiationException(String message, Throwable cause) {
            super(message, cause);
        }

    }

    private RulesEngineParameters parameters;

    private List<RuleListener> ruleListeners;

    private Class<E> rulesEngineClass;

    public static RulesEngineBuilder<DefaultRulesEngine> aNewRulesEngine() {
        return new RulesEngineBuilder<DefaultRulesEngine>(DefaultRulesEngine.class);
    }

    public static <E extends RulesEngine> RulesEngineBuilder<E> aNewRulesEngine(Class<E> rulesEngineClass) {
        return new RulesEngineBuilder<E>(rulesEngineClass);
    }

    private RulesEngineBuilder(Class<E> rulesEngineClass) {
        this.parameters = new RulesEngineParameters(Utils.DEFAULT_ENGINE_NAME, false, false, Utils.DEFAULT_RULE_PRIORITY_THRESHOLD, false);
        this.ruleListeners = new ArrayList<>();
        this.rulesEngineClass = rulesEngineClass;
    }

    public RulesEngineBuilder<E> named(final String name) {
        parameters.setName(name);
        return this;
    }

    public RulesEngineBuilder<E> withSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        parameters.setSkipOnFirstAppliedRule(skipOnFirstAppliedRule);
        return this;
    }

    public RulesEngineBuilder<E> withSkipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        parameters.setSkipOnFirstNonTriggeredRule(skipOnFirstNonTriggeredRule);
        return this;
    }

    public RulesEngineBuilder<E> withSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        parameters.setSkipOnFirstFailedRule(skipOnFirstFailedRule);
        return this;
    }

    public RulesEngineBuilder<E> withRulePriorityThreshold(final int priorityThreshold) {
        parameters.setPriorityThreshold(priorityThreshold);
        return this;
    }

    public RulesEngineBuilder<E> withRuleListener(final RuleListener ruleListener) {
        this.ruleListeners.add(ruleListener);
        return this;
    }

    public RulesEngineBuilder<E> withSilentMode(final boolean silentMode) {
        parameters.setSilentMode(silentMode);
        return this;
    }

    public E build() {
        try {
            return this.getRulesEngineConstructors().newInstance(parameters, ruleListeners);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException exception) {
            throw new EngineInstantiationException("Cannot instantiate the new rules engine", exception);
        }
    }

    /**
     * Search the rules engine class for the appropriate constructor for the new rules engine
     * 
     * @return rules engine constructor
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    @SuppressWarnings("unchecked")
    protected Constructor<E> getRulesEngineConstructors() throws SecurityException, NoSuchMethodException {
        Constructor<?> constructor = null;
        for (Constructor<?> aConstructor : this.rulesEngineClass.getDeclaredConstructors()) {
            Class<?>[] parameters = aConstructor.getParameterTypes();
            if ((parameters.length == 2) && RulesEngineParameters.class.isAssignableFrom(parameters[0]) && List.class.isAssignableFrom(parameters[1])) {
                constructor = aConstructor;
                break;
            }
        }
        if (constructor == null) {
            throw new NoSuchMethodException("Cannot find a constructor for the rules engine class " + this.rulesEngineClass);
        }
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        return (Constructor<E>) constructor;
    }

}
