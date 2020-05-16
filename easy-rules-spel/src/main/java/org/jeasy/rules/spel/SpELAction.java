/*
 * The MIT License
 *
 *  Copyright (c) 2020, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.spel;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * This class is an implementation of {@link Action} that uses
 * <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions">SpEL</a>
 * to execute the action.
 *
 * Each fact is set as a variable in the {@link org.springframework.expression.EvaluationContext}.
 *
 * The facts map is set as the root object of the {@link org.springframework.expression.EvaluationContext}.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class SpELAction implements Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpELAction.class);

    private final ExpressionParser parser = new SpelExpressionParser();

    private final String expression;
    private final Expression compiledExpression;
    private BeanResolver beanResolver;

    /**
     * Create a new {@link SpELAction}.
     *
     * @param expression the action written in expression language
     */
    public SpELAction(String expression) {
        this(expression, ParserContext.TEMPLATE_EXPRESSION);
    }

    /**
     * Create a new {@link SpELAction}.
     *
     * @param expression    the action written in expression language
     * @param beanResolver  the bean resolver used to resolve bean references
     */
    public SpELAction(String expression, BeanResolver beanResolver) {
        this(expression, ParserContext.TEMPLATE_EXPRESSION, beanResolver);
    }

    /**
     * Create a new {@link SpELAction}.
     *
     * @param expression the action written in expression language
     * @param parserContext the SpEL parser context
     */
    public SpELAction(String expression, ParserContext parserContext) {
        this.expression = expression;
        compiledExpression = parser.parseExpression(expression, parserContext);
    }

    /**
     * Create a new {@link SpELAction}.
     *
     * @param expression    the action written in expression language
     * @param beanResolver  the bean resolver used to resolve bean references
     * @param parserContext the SpEL parser context
     */
    public SpELAction(String expression, ParserContext parserContext, BeanResolver beanResolver) {
        this.expression = expression;
        this.beanResolver = beanResolver;
        compiledExpression = parser.parseExpression(expression, parserContext);
    }

    @Override
    public void execute(Facts facts) {
        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setRootObject(facts.asMap());
            context.setVariables(facts.asMap());
            if (beanResolver != null) {
                context.setBeanResolver(beanResolver);
            }
            compiledExpression.getValue(context);
        } catch (Exception e) {
            LOGGER.error("Unable to evaluate expression: '" + expression + "' on facts: " + facts, e);
            throw e;
        }
    }
}
