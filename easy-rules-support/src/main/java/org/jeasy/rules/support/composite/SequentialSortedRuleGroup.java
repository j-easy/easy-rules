package org.jeasy.rules.support.composite;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Base class representing a composite rule composed of a list of rules that are executed in order.
 * rules are sorted by their natural order (priority by default) within the group.
 *
 * <strong>This class is not thread-safe.
 * Sub-classes are inherently not thread-safe.</strong>
 *
 * @author Jaylee Ibrahim (mcaibai-std@yahoo.com)
 */
public class SequentialSortedRuleGroup extends SequentialRuleGroup {

  public SequentialSortedRuleGroup(String name, String description) {
    super(name, description);
  }

  public SequentialSortedRuleGroup(String name, String description, int priority) {
    super(name, description, priority);
  }

  @Override
  protected Collection getCollectionStore() {
    return new TreeSet();
  }

}
