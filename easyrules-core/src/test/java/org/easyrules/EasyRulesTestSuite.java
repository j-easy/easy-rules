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
package org.easyrules;

import junit.framework.TestSuite;
import org.easyrules.core.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for Easy Rules core module.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AnnotationInheritanceTest.class,
        BasicRuleTest.class,
        RulePriorityThresholdTest.class,
        SkipOnFirstAppliedRuleTest.class,
        SkipOnFirstFailedRuleTest.class,
        SkipOnFirstNonTrigeredRuleTest.class,
        RuleListenerTest.class,
        CustomRuleOrderingTest.class,
        RuleProxyTest.class,
        RulesEngineBuilderTest.class,
        CompositeRuleTest.class,
        RuleDefinitionValidatorTest.class,
        DefaultRulesEngineTest.class,
        UtilsTest.class})
public class EasyRulesTestSuite extends TestSuite {

}
