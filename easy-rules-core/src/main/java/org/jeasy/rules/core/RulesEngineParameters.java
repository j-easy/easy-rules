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
package org.jeasy.rules.core;

/**
 * Parameters of the rules engine.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineParameters {

    /**
     * Default rule priority threshold.
     */
    public static final int DEFAULT_RULE_PRIORITY_THRESHOLD = Integer.MAX_VALUE;
    
    /**
     * Parameter to skip next applicable rules when a rule is applied.
     */
    private boolean skipOnFirstAppliedRule;

    /**
     * Parameter to skip next applicable rules when a rule is non triggered
     */
    private boolean skipOnFirstNonTriggeredRule;

    /**
     * Parameter to skip next applicable rules when a rule has failed.
     */
    private boolean skipOnFirstFailedRule;

    /**
     * Parameter to skip next rules if priority exceeds a user defined threshold.
     */
    private int priorityThreshold;

    /**
     * Create a new {@link RulesEngineParameters} with default values.
     */
    public RulesEngineParameters() {
        this.priorityThreshold = RulesEngineParameters.DEFAULT_RULE_PRIORITY_THRESHOLD;
    }

    /**
     * Create a new {@link RulesEngineParameters}
     * @deprecated Use {@link RulesEngineParameters#RulesEngineParameters(boolean, boolean, boolean, int)} instead.
     * <strong>This constructor will be removed in v3.2</strong>
     */
    @Deprecated
    public RulesEngineParameters(final boolean skipOnFirstAppliedRule, final boolean skipOnFirstFailedRule, final int priorityThreshold, final boolean silentMode) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        this.priorityThreshold = priorityThreshold;
    }

    /**
     * Create a new {@link RulesEngineParameters}.
     * @deprecated Use {@link RulesEngineParameters#RulesEngineParameters(boolean, boolean, boolean, int)} instead.
     * <strong>This constructor will be removed in v3.2</strong>
     */
    @Deprecated
    public RulesEngineParameters(final boolean skipOnFirstAppliedRule, final boolean skipOnFirstFailedRule, final boolean skipOnFirstNonTriggeredRule, final int priorityThreshold, final boolean silentMode) {
        this(skipOnFirstAppliedRule, skipOnFirstFailedRule, skipOnFirstNonTriggeredRule, priorityThreshold);
    }

    /**
     * Create a new {@link RulesEngineParameters}.
     *
     * @param skipOnFirstAppliedRule parameter to skip next applicable rules on first applied rule.
     * @param skipOnFirstFailedRule parameter to skip next applicable rules on first failed rule.
     * @param skipOnFirstNonTriggeredRule parameter to skip next applicable rules on first non triggered rule.
     * @param priorityThreshold threshold after which rules should be skipped.
     */
    public RulesEngineParameters(final boolean skipOnFirstAppliedRule, final boolean skipOnFirstFailedRule, final boolean skipOnFirstNonTriggeredRule, final int priorityThreshold) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
        this.priorityThreshold = priorityThreshold;
    }

    public int getPriorityThreshold() {
        return priorityThreshold;
    }

    /**
     * @deprecated Silent mode is now log implementation config. Now it uses slf4j facade
     * <strong>This will be removed in v3.2</strong>
     */
    @Deprecated
    public boolean isSilentMode() {
        return false;
    }

    /**
     * @deprecated Silent mode is now log implementation config. Now it uses slf4j facade
     * <strong>This will be removed in v3.2</strong>
     */
    @Deprecated
    public void setSilentMode(final boolean silentMode) {

    }

    public void setPriorityThreshold(final int priorityThreshold) {
        this.priorityThreshold = priorityThreshold;
    }

    public boolean isSkipOnFirstAppliedRule() {
        return skipOnFirstAppliedRule;
    }

    public void setSkipOnFirstAppliedRule(final boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    public boolean isSkipOnFirstNonTriggeredRule() {
        return skipOnFirstNonTriggeredRule;
    }

    public void setSkipOnFirstNonTriggeredRule(final boolean skipOnFirstNonTriggeredRule) {
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
    }

    public boolean isSkipOnFirstFailedRule() {
        return skipOnFirstFailedRule;
    }

    public void setSkipOnFirstFailedRule(final boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
    }

    @Override
    public String toString() {
        return "Engine parameters { " +
                "skipOnFirstAppliedRule=" + skipOnFirstAppliedRule +
                ", skipOnFirstNonTriggeredRule=" + skipOnFirstNonTriggeredRule +
                ", skipOnFirstFailedRule=" + skipOnFirstFailedRule +
                ", priorityThreshold=" + priorityThreshold +
                " }";
    }
}
