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
package org.easyrules.spring.boot.autoconfigure;

import org.easyrules.annotation.Rule;
import org.easyrules.api.RulesEngine;
import org.easyrules.spring.RulesEngineFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * AutoConfiguration for {@link RulesEngine}
 *
 * @author Kyle Anderson
 */
@Configuration
@ConditionalOnClass(RulesEngineFactoryBean.class)
@ConditionalOnMissingBean(RulesEngineFactoryBean.class)
public class EasyRulesAutoConfiguration {

    @Bean
    public RulesEngineFactoryBean rulesEngineFactoryBean(ApplicationContext context) {
        List<Object> rulesFromRuleAnnotation = new ArrayList<>(context.getBeansWithAnnotation(Rule.class).values());
        List<org.easyrules.api.Rule> rulesFromRuleImplementation = new ArrayList<>(context.getBeansOfType(org.easyrules.api.Rule.class).values());
        List<Object> rules = new ArrayList<>();
        rules.addAll(rulesFromRuleAnnotation);
        rules.addAll(rulesFromRuleImplementation);
        RulesEngineFactoryBean rulesEngineFactoryBean = new RulesEngineFactoryBean();
        rulesEngineFactoryBean.setRules(rules);
        return rulesEngineFactoryBean;
    }

}
