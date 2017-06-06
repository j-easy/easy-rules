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
     */
    public void put(String name, Object fact) {
        Objects.requireNonNull(name);
        facts.put(name, fact);
    }

    /**
     * Remove fact.
     *
     * @param name of fact to remove
     */
    public void remove(String name) {
        Objects.requireNonNull(name);
        facts.remove(name);
    }

    /**
     * Get a fact by name.
     *
     * @param name of fact to get.
     * @return the fact having the given name, or null if there is no fact with the given name
     */
    public Object get(String name) {
        Objects.requireNonNull(name);
        return facts.get(name);
    }

    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return facts.entrySet().iterator();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Facts {").append("\n");
        for (Map.Entry<String, Object> fact : facts.entrySet()) {
            stringBuilder.append(format("   Fact { %s : %s }", fact.getKey(), fact.getValue().toString()));
            stringBuilder.append("\n");
        }
        stringBuilder.append("}");
        return  stringBuilder.toString();
    }
}
