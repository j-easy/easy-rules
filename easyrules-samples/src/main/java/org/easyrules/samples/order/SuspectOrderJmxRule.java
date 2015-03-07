/*
 * The MIT License
 *
 *  Copyright (c) 2014, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
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

package org.easyrules.samples.order;

import org.easyrules.jmx.api.JMXRule;

import javax.management.MXBean;

/**
 * Interface to make suspect order rule manageable via JMX.<br/>
 * Suspect order threshold should be changed at runtime.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */

@MXBean
public interface SuspectOrderJmxRule extends JMXRule {

    /**
     * Get the current suspect order amount threshold
     * @return current suspect order amount threshold
     */
    float getSuspectOrderAmountThreshold();

    /**
     * Set the suspect order amount threshold
     * @param suspectOrderAmountThreshold the new suspect order amount threshold
     */
    void setSuspectOrderAmountThreshold(float suspectOrderAmountThreshold);

}