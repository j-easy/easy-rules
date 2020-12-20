package org.jeasy.rules.starter.config;

import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.api.RulesEngineParameters;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.starter.props.EasyRulesProperties;
import org.jeasy.rules.starter.support.EasyRulesTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Auto config
 *
 * @author venus
 * @version 1
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(EasyRulesProperties.class)
@ConditionalOnProperty(prefix = "easy.rules", name = "enabled", matchIfMissing = true)
public class EasyRulesAutoConfiguration {

    @Bean
    @Primary
    public RulesEngine defaultRulesEngine(EasyRulesProperties properties) {
        return new DefaultRulesEngine(
            new RulesEngineParameters()
                .priorityThreshold(properties.getRulePriorityThreshold())
                .skipOnFirstAppliedRule(properties.isSkipOnFirstAppliedRule())
                .skipOnFirstFailedRule(properties.isSkipOnFirstFailedRule())
                .skipOnFirstNonTriggeredRule(properties.isSkipOnFirstNonTriggeredRule())
        );
    }

    @Bean
    public RulesEngine inferenceRulesEngine(EasyRulesProperties properties) {
        return new InferenceRulesEngine(
            new RulesEngineParameters()
                .priorityThreshold(properties.getRulePriorityThreshold())
                .skipOnFirstAppliedRule(properties.isSkipOnFirstAppliedRule())
                .skipOnFirstFailedRule(properties.isSkipOnFirstFailedRule())
                .skipOnFirstNonTriggeredRule(properties.isSkipOnFirstNonTriggeredRule())
        );
    }

    @Bean
    public EasyRulesTemplate easyRulesTemplate(RulesEngine rulesEngine, ApplicationContext applicationContext) {
        return new EasyRulesTemplate(rulesEngine, applicationContext);
    }
}
