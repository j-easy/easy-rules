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

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompositeRuleType {
  UNIT_RULE_GROUP(UnitRuleGroup.class.getSimpleName()) {
    @Override
    public CompositeRule newInstance(String ruleName) {
      return new UnitRuleGroup(ruleName);
    }
  },
  CONDITIONAL_RULE_GROUP(ConditionalRuleGroup.class.getSimpleName()) {
    @Override
    public CompositeRule newInstance(String ruleName) {
      return new ConditionalRuleGroup(ruleName);
    }

  },
  ACTIVATION_RULE_GROUP(ActivationRuleGroup.class.getSimpleName()) {
    @Override
    public CompositeRule newInstance(String ruleName) {
      return new ActivationRuleGroup(ruleName);
    }
  },
  SEQUENTIAL_RULE_GROUP(SequentialRuleGroup.class.getSimpleName()) {
    @Override
    public CompositeRule newInstance(String ruleName) {
      return new SequentialRuleGroup(ruleName);
    }
  },
  SEQUENTIAL_SORTED_RULE_GROUP(SequentialSortedRuleGroup.class.getSimpleName()) {
    @Override
    public CompositeRule newInstance(String ruleName) {
      return new SequentialSortedRuleGroup(ruleName);
    }
  },
  UNKNOWN("unknown") {
    @Override
    public CompositeRule newInstance(String ruleName) {
      String msg =
          "Invalid composite rule type, must be one of " + getSupportedTypeNames() +
              ", ruleName=" + ruleName;
      throw new IllegalArgumentException(msg);
    }
  };

  private String typeName;

  public static CompositeRuleType fromTypeName(String typeName) {
    return Arrays.stream(CompositeRuleType.values())
        .filter(e -> e.getTypeName().equals(typeName))
        .findFirst()
        .orElse(UNKNOWN);
  }

  public static String getSupportedTypeNames() {
    return Arrays.stream(values())
        .map(CompositeRuleType::getTypeName)
        .collect(Collectors.joining());
  }

  public abstract CompositeRule newInstance(String ruleName);
}
