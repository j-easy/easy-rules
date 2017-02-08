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
package org.easyrules.core;

/**
 * Parameters of the rules engine.
 * 
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class RulesEngineParameters {

    /**
     * The engine name.
     */
    protected String name;
    
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
     * Parameter to mute loggers.
     */
    private boolean silentMode;

    public RulesEngineParameters(String name, boolean skipOnFirstAppliedRule, boolean skipOnFirstFailedRule, int priorityThreshold, boolean silentMode) {
        this.name = name;
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
        this.priorityThreshold = priorityThreshold;
        this.silentMode = silentMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriorityThreshold() {
        return priorityThreshold;
    }

    public void setPriorityThreshold(int priorityThreshold) {
        this.priorityThreshold = priorityThreshold;
    }

    public boolean isSilentMode() {
        return silentMode;
    }

    public void setSilentMode(boolean silentMode) {
        this.silentMode = silentMode;
    }

    public boolean isSkipOnFirstAppliedRule() {
        return skipOnFirstAppliedRule;
    }

    public void setSkipOnFirstAppliedRule(boolean skipOnFirstAppliedRule) {
        this.skipOnFirstAppliedRule = skipOnFirstAppliedRule;
    }

    public boolean isSkipOnFirstNonTriggeredRule() {
        return skipOnFirstNonTriggeredRule;
    }

    public void setSkipOnFirstNonTriggeredRule(boolean skipOnFirstNonTriggeredRule) {
        this.skipOnFirstNonTriggeredRule = skipOnFirstNonTriggeredRule;
    }

    public boolean isSkipOnFirstFailedRule() {
        return skipOnFirstFailedRule;
    }

    public void setSkipOnFirstFailedRule(boolean skipOnFirstFailedRule) {
        this.skipOnFirstFailedRule = skipOnFirstFailedRule;
    }
}
