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

package org.jeasy.rules.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * Basic rule implementation class that provides common methods.
 * <p>
 * You can extend this class and override {@link BasicRule#evaluate(Facts)} and {@link
 * BasicRule#execute(Facts)} to provide rule conditions and actions logic.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@Getter
@Setter
@ToString(of = {"name", "description", "priority"})
@EqualsAndHashCode
public class BasicRule implements Rule {

  /**
   * Rule name. Rules are unique according to their names within a rules engine registry.
   */
  protected String name;

  /**
   * Rule description.
   */
  protected String description;

  /**
   * Rule priority.
   */
  protected int priority;

  /**
   * Create a new {@link BasicRule}.
   */
  public BasicRule() {
    this(Rule.DEFAULT_NAME, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link BasicRule}.
   *
   * @param name rule name
   */
  public BasicRule(final String name) {
    this(name, Rule.DEFAULT_DESCRIPTION, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link BasicRule}.
   *
   * @param name        rule name
   * @param description rule description
   */
  public BasicRule(final String name, final String description) {
    this(name, description, Rule.DEFAULT_PRIORITY);
  }

  /**
   * Create a new {@link BasicRule}.
   *
   * @param name        rule name
   * @param description rule description
   * @param priority    rule priority
   */
  public BasicRule(final String name, final String description, final int priority) {
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  /**
   * {@inheritDoc}
   */
  public boolean evaluate(Facts facts) {
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void execute(Facts facts) throws Exception {
    // no op
  }

  @Override
  public int compareTo(final Rule rule) {
    if (getPriority() < rule.getPriority()) {
      return -1;
    } else if (getPriority() > rule.getPriority()) {
      return 1;
    } else {
      return getName().compareTo(rule.getName());
    }
  }

}
