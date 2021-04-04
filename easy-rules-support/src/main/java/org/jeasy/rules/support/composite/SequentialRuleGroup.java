/*
 * The MIT License
 *
 *  Copyright (c) 2021, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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

package org.jeasy.rules.support.composite;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.core.BasicRule;
import org.jeasy.rules.core.RuleProxy;

/**
 * Base class representing a composite rule composed of a list of rules that are executed in their
 * order of encounter i.e., the priority flag is ignored.
 *
 * <strong>This class is not thread-safe.
 * Sub-classes are inherently not thread-safe.</strong>
 *
 * @author Jaylee Ibrahim (mcaibai-std@yahoo.com)
 */
public class SequentialRuleGroup extends BasicRule {

  /**
   * The list of comprised rules.
   */
  protected Collection<Rule> rules;
  private final Map<Object, Rule> proxyRules;

  /**
   * Create a new {@link SequentialRuleGroup}.
   */
  public SequentialRuleGroup() {
    this(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link SequentialRuleGroup}.
   *
   * @param name rule name
   */
  public SequentialRuleGroup(final String name) {
    this(name, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link SequentialRuleGroup}.
   *
   * @param name        rule name
   * @param description rule description
   */
  public SequentialRuleGroup(final String name, final String description) {
    this(name, description, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link SequentialRuleGroup}.
   *
   * @param name        rule name
   * @param description rule description
   * @param priority    rule priority
   */
  public SequentialRuleGroup(final String name, final String description, final int priority) {
    super(name, description, priority);
    rules = getCollectionStore();
    proxyRules = new HashMap<>();
  }

  protected Collection getCollectionStore() {
    return new ArrayList<>();
  }

  @Override
  public boolean evaluate(Facts facts) {
    return true;
  }

  @Override
  public void execute(Facts facts) throws Exception {
    for (Rule rule : rules) {
      if (rule.evaluate(facts)) {
        rule.execute(facts);
      }
    }
  }

  /**
   * Add a rule to the composite rule.
   *
   * @param rule the rule to add
   */
  public void addRule(final Object rule) {
    Rule proxy = RuleProxy.asRule(rule);
    rules.add(proxy);
    proxyRules.put(rule, proxy);
  }

  /**
   * Remove a rule from the composite rule.
   *
   * @param rule the rule to remove
   */
  public void removeRule(final Object rule) {
    Rule proxy = proxyRules.get(rule);
    if (proxy != null) {
      rules.remove(proxy);
    }
  }

}
