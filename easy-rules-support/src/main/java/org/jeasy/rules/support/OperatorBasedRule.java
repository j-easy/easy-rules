package org.jeasy.rules.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;

public class OperatorBasedRule extends CompositeRule{

	private Set<Rule> successfulEvaluations;
  //  private Rule conditionalRule;
    
       
    /**
     * Create a conditional rule group.
     *
     * @param name of the conditional rule
     */
    public OperatorBasedRule(String name) {
    	super(name);
    	rules = new TreeSet<>();
    	successfulEvaluations =  new HashSet<>();
    }

    /**
     * Create a operator based rule group.
     *
     * @param name        of the conditional rule
     * @param description of the conditional rule
     */
    public OperatorBasedRule(String name, String description) {
        super(name, description);
        rules = new TreeSet<>();
    }

    /**
     * Create a operator based rule group.
     *
     * @param name        of the conditional rule
     * @param description of the conditional rule
     * @param priority    of the composite rule
     */
    public OperatorBasedRule(String name, String description, int priority) {
        super(name, description, priority);
        rules = new TreeSet<>();
    }
    
	@Override
	public boolean evaluate(Facts facts) {
		
		for (Rule rule : rules) {
			System.out.println(rule.getName());
			System.out.println(facts.toString());
            if (rule.evaluate(facts)) {
            	 successfulEvaluations.add(rule);
            }
        }
		return false;
	}

	@Override
	public void execute(Facts facts) throws Exception {
		
		for(Rule rule : rules)
		{
			
			rule.execute(facts);
		}
		
	}
	
	public OperatorBasedRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Rule getRuleWithHighestPriority() {
        List<Rule> copy = sort(rules);
        // make sure that we only have one rule with the highest priority
        Rule highest = copy.get(0);
        if (copy.size() > 1 && copy.get(1).getPriority() == highest.getPriority()) {
           throw new IllegalArgumentException("Only one rule can have highest priority");
        }
        return highest;
    }

    private List<Rule> sort(Set<Rule> rules) {
        return new ArrayList<>(new TreeSet<>(rules));
    }

    public void addRules(Rules rules)
    {
    	for(Rule rule2 : rules)
    	{
    		//System.out.println("rule2@@@@@"+rule2);
    		this.rules.add(rule2);
    	}
    		
    }
    
}
