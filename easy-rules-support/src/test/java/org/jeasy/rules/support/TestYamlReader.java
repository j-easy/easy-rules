package org.jeasy.rules.support;

public class TestYamlReader {

	public static void main(String[] args) {
		
		YamlRuleDefinitionReaderTest definitionReaderTest = new YamlRuleDefinitionReaderTest();
		
		try {
			definitionReaderTest.testRuleDefinitionReadingFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
