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
package org.jeasy.rules.api;

import java.util.*;

import static java.lang.String.format;

/**
 * Represents a set of named facts. Facts have unique name within a <code>Facts</code> object.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class Facts implements Iterable<Map.Entry<String, Object>> {

    private Map<String, Object> facts = new HashMap<>();

    /**
     * Put a fact in the working memory.
     * This will replace any fact having the same name.
     *
     * @param name fact name
     * @param fact object to put in the working memory
     * @return the previous value associated with <tt>name</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>name</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>name</tt>.)
     */
    public Object put(String name, Object fact) {
        Objects.requireNonNull(name);
        return facts.put(name, fact);
    }

    /**
     * Remove fact.
     *
     * @param name of fact to remove
     * @return the previous value associated with <tt>name</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>name</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>name</tt>.)
     */
    public Object remove(String name) {
        Objects.requireNonNull(name);
        return facts.remove(name);
    }

    /**
     * Get a fact by name.
     *
     * @param name of the fact
     * @param <T> type of the fact
     * @return the fact having the given name, or null if there is no fact with the given name
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        Objects.requireNonNull(name);
        return (T) facts.get(name);
    }

    /**
     * Return a copy of the facts as a map. It is not intended to manipulate
     * facts outside of the rules engine (aka other than manipulating them through rules).
     *
     * @return a copy of the current facts as a {@link HashMap}
     */
    public Map<String, Object> asMap() {
        return new HashMap<>(facts);
    }

    /**
     * Return an iterator on the set of facts. It is not intended to remove
     * facts using this iterator outside of the rules engine (aka other than doing it through rules)
     * 
     * @return an iterator on the set of facts
     */
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return facts.entrySet().iterator();
    }

    /**
     * Clear facts.
     */
    public void clear() {
        facts.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        List<Map.Entry<String, Object>> entries = new ArrayList<>(facts.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, Object> entry = entries.get(i);
            stringBuilder.append(format(" { %s : %s } ", entry.getKey(), entry.getValue()));
            if (i < entries.size() - 1) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("]");
        return  stringBuilder.toString();
    }
}
