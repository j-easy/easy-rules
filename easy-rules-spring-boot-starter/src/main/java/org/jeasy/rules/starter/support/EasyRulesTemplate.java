package org.jeasy.rules.starter.support;

import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.starter.exception.InvalidRuleBeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Convenient operations used to fire rules.
 *
 * @author venus
 * @version 1
 */
public class EasyRulesTemplate implements InitializingBean {

    /**
     * The default name of only one fact object
     */
    public static final String DEFAULT_FACT_NAME = "default";

    /**
     * Used to store Rules objects which represent a collection of rules.
     */
    private static final Map<String, Rules> RULES_MAP = new HashMap<>(64);

    /**
     * The important engine object.
     */
    private final RulesEngine rulesEngine;

    /**
     * Known spring application context object
     */
    private final ApplicationContext applicationContext;


    public EasyRulesTemplate(RulesEngine rulesEngine, ApplicationContext applicationContext) {
        this.rulesEngine = rulesEngine;
        this.applicationContext = applicationContext;
    }

    /**
     * Check weather the Group exists or not
     * thrown exception if checking failure
     * @param groupName group name
     */
    private void checkGroupName(String groupName) {
        if (!RULES_MAP.containsKey(groupName)) {
            throw new IllegalArgumentException("Rule group " + groupName + " not exists.");
        }
    }

    /**
     * Fire a group rules
     * @param groupName group name
     * @param fact a fact object
     */
    public void fire(String groupName, Object fact) {
        this.checkGroupName(groupName);
        Objects.requireNonNull(fact, "Fact object must not be NULL.");
        Facts facts = new Facts();
        facts.put(DEFAULT_FACT_NAME, fact);
        this.rulesEngine.fire(RULES_MAP.get(groupName), facts);
    }

    /**
     * Fire a group rules
     * @param groupName group name
     * @param factName the name of fact object
     * @param fact fact object
     */
    public void fire(String groupName, String factName, Object fact) {
        this.checkGroupName(groupName);
        Objects.requireNonNull(fact, "Fact object must not be NULL.");
        Facts facts = new Facts();
        facts.put(StringUtils.hasText(factName) ? factName : DEFAULT_FACT_NAME, fact);
        this.rulesEngine.fire(RULES_MAP.get(groupName), facts);
    }

    /**
     * Fire a group rules
     * @param groupName group name
     * @param factMap facts
     */
    public void fire(String groupName, Map<String, Object> factMap) {
        this.checkGroupName(groupName);
        Assert.notEmpty(factMap, "Facts map must not be EMPTY.");
        Facts facts = new Facts();
        factMap.forEach(facts::put);
        this.rulesEngine.fire(RULES_MAP.get(groupName), facts);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(rulesEngine, "Rules engine must not be null.");

        // fetch all rule beans
        Map<String, Object> ruleBeans = this.applicationContext.getBeansWithAnnotation(RuleGroup.class);

        // validate
        if (ruleBeans.values().stream().anyMatch(ruleBean -> !AnnotationUtils.isCandidateClass(ruleBean.getClass(), Rule.class))) {
            throw new InvalidRuleBeanException("Rule BEAN must be annotated with @Rule annotation.");
        }

        // group by group name specified by the name() method of @RuleGroup annotation
        Map<String, List<Object>> ruleBeanGroups = ruleBeans.values().stream().collect(
            Collectors.groupingBy(
                ruleBean -> Objects.requireNonNull(AnnotationUtils.findAnnotation(ruleBean.getClass(), RuleGroup.class)).name()
            )
        );

        // store for using after
        ruleBeanGroups.forEach((key, rulesBeans) -> RULES_MAP.put(
            key,
            new Rules(rulesBeans.toArray())
        ));
    }
}
