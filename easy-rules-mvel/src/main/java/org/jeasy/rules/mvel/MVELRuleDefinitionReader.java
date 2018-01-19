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
package org.jeasy.rules.mvel;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
class MVELRuleDefinitionReader {

    private Yaml yaml = new Yaml();

    MVELRuleDefinition read(File descriptor) throws FileNotFoundException {
        Object object = yaml.load(new FileReader(descriptor));
        Map<String, Object> map = (Map<String, Object>) object;
        return createRuleDefinitionFrom(map);
    }

    MVELRuleDefinition read(String descriptor) throws NullPointerException {
        Object object = yaml.load(descriptor);
        Map<String, Object> map = (Map<String, Object>) object;
        return createRuleDefinitionFrom(map);
    }

    private static MVELRuleDefinition createRuleDefinitionFrom(Map<String, Object> map) {
        MVELRuleDefinition ruleDefinition = new MVELRuleDefinition();
        ruleDefinition.setName((String) map.get("name"));
        ruleDefinition.setDescription((String) map.get("description"));
        ruleDefinition.setPriority((Integer) map.get("priority"));
        ruleDefinition.setCondition((String) map.get("condition"));
        ruleDefinition.setActions((List<String>) map.get("actions"));
        return ruleDefinition;
    }
}
