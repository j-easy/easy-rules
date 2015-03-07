/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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


/**
 * Abstraction for a rule that can be fired by the rules engine.
 *
 * Rules are registered in the rules engine registry and must have a <strong>unique</strong> name.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public interface Rule {

    /**
     * Getter for rule name.
     * @return the rule name
     */
    String getName();

    /**
     * Getter for rule description.
     * @return rule description
     */
    String getDescription();

    /**
     * Setter for rule description.
     * @param description new rule description
     */
    void setDescription(String description);

    /**
     * Getter for rule priority.
     * @return rule priority
     */
    int getPriority();

    /**
     * Setter for rule priority.
     * @param priority the priority to set
     */
    void setPriority(int priority);

    /**
     * Rule conditions abstraction : this method encapsulates the rule's conditions.
     * @return true if the rule should be applied, false else
     */
    boolean evaluateConditions();

    /**
     * Rule actions abstraction : this method encapsulates the rule's actions.
     * @throws Exception thrown if an exception occurs during actions performing
     */
    void performActions() throws Exception;

}
