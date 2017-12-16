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
