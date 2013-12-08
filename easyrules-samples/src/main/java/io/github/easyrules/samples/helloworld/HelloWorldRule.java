/*
 * The MIT License
 *
 *  Copyright (c) 2013, benas (md.benhassine@gmail.com)
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

package io.github.easyrules.samples.helloworld;

import io.github.easyrules.core.BasicRule;

/**
 * Hello World rule class.
 *
 * @author benas (md.benhassine@gmail.com)
 */
public class HelloWorldRule extends BasicRule {

    /**
     * The user input
     */
    private String input;

    public HelloWorldRule(String name, String description, int priority) {
        super(name, description, priority);
    }

    @Override
    public boolean evaluateConditions() {
        //The rule should be applied only if the user's response is yes (duke friend)
        return input.equalsIgnoreCase("yes");
    }

    @Override
    public void performActions() throws Exception {
        //When rule conditions are satisfied, prints 'Hello duke's friend!' to the console
        System.out.println("Hello duke's friend!");
    }

    public void setInput(String input) {
        this.input = input;
    }
}
