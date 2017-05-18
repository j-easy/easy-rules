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
package org.easyrules.api;

import org.easyrules.core.RuleProxy;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Rules implements Iterable<Rule> {

    private Set<Rule> rules = new TreeSet<>();

    public Rules(Set<Rule> rules) {
        this.rules = rules;
    }

    public Rules(Rule... rules ) {
        Collections.addAll(this.rules, rules);
    }

    public Rules(Object... rules ) {
        for (Object rule : rules) {
            this.register(RuleProxy.asRule(rule));
        }
    }

    public void register(Object rule) {
        rules.add(RuleProxy.asRule(rule));
    }

    public void unregister(Object rule) {
        rules.remove(RuleProxy.asRule(rule));
    }

    public boolean isEmpty() {
        return rules.isEmpty();
    }

    public void clear() {
        rules.clear();
    }

    @Override
    public Iterator<Rule> iterator() {
        return rules.iterator();
    }

    public void sort() {
        rules = new TreeSet<>(rules);
    }
}
