package org.jeasy.rules.mvel;

import org.jeasy.rules.api.Action;
import org.jeasy.rules.api.Facts;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.assertj.core.api.Assertions.assertThat;

public class MVELActionTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Test
    public void testMVELActionExecution() throws Exception {
        // given
        Action markAsAdult = new MVELAction("person.setAdult(true);");
        Facts facts = new Facts();
        Person foo = new Person("foo", 20);
        facts.put("person", foo);

        // when
        markAsAdult.execute(facts);

        // then
        assertThat(foo.isAdult()).isTrue();
    }

    @Test
    public void testMVELFunctionExecution() throws Exception {
        // given
        Action printAction = new MVELAction("def hello() { System.out.println(\"Hello from MVEL!\"); }; hello();");
        Facts facts = new Facts();

        // when
        printAction.execute(facts);

        // then
        assertThat(systemOutRule.getLog()).contains("Hello from MVEL!");
    }
}