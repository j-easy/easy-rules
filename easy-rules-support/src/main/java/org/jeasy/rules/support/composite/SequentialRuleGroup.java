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

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;

/**
 * Base class representing a composite rule composed of a list of rules that are executed in their
 * order of encounter i.e., the priority flag is ignored.
 *
 * <strong>This class is not thread-safe.
 * Sub-classes are inherently not thread-safe.</strong>
 *
 * @author Jaylee Ibrahim (mcaibai-std@yahoo.com)
 */
public class SequentialRuleGroup extends CompositeRule {

  public SequentialRuleGroup() {
  }

  public SequentialRuleGroup(String name) {
    super(name);
  }

  public SequentialRuleGroup(String name, String description) {
    super(name, description);
  }

  public SequentialRuleGroup(String name, String description, int priority) {
    super(name, description, priority);
  }

  @Override
  public boolean evaluate(Facts facts) {
    return true;
  }

  @Override
  public boolean isSortable() {
    return false;
  }

  @Override
  public void execute(Facts facts) throws Exception {
    for (Rule rule : rules) {
      if (rule.evaluate(facts)) {
        rule.execute(facts);
      }
    }
  }

}
