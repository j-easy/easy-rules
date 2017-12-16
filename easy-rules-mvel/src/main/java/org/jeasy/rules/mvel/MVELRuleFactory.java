package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Rule;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Factory to create {@link MVELRule} instances.
 *
 * @author Mahmoud Ben Hassine (mahmoud.benhassine@icloud.com)
 */
public class MVELRuleFactory {

    private static MVELRuleDefinitionReader reader = new MVELRuleDefinitionReader();

    /**
     * Create a new {@link MVELRule} from a rule descriptor.
     * @param ruleDescriptor in yaml format
     * @return a new rule
     * @throws FileNotFoundException if the rule descriptor cannot be found
     */
    public static Rule createRuleFrom(File ruleDescriptor) throws FileNotFoundException {
        MVELRuleDefinition ruleDefinition = reader.read(ruleDescriptor);
        return ruleDefinition.create();
    }

}
