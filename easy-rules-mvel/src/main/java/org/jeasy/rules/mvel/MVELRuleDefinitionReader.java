/**
 * The MIT License
 *
 *  Copyright (c) 2019, Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
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
package org.jeasy.rules.mvel;

import java.io.Reader;
import java.util.List;

/**
 * Strategy interface for {@link MVELRuleDefinition} readers.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 * @see MVELJsonRuleDefinitionReader
 * @see MVELYamlRuleDefinitionReader
 */
public interface MVELRuleDefinitionReader {

    /**
     * Read a list of rule definitions from a rules descriptor.
     *
     * <strong> The descriptor is expected to contain a collection of rule definitions
     * even for a single rule.</strong>
     *
     * @param reader of the rules descriptor
     * @return a list of rule definitions
     * @throws Exception if a problem occurs during rule defintion parsing
     */
    List<MVELRuleDefinition> read(Reader reader) throws Exception;

}
