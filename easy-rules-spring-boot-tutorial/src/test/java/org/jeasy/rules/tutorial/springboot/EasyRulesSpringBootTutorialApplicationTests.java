package org.jeasy.rules.tutorial.springboot;

import org.jeasy.rules.starter.support.EasyRulesTemplate;
import org.jeasy.rules.tutorial.springboot.pojo.MyOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EasyRulesSpringBootTutorialApplicationTests {

    @Autowired
    private EasyRulesTemplate easyRulesTemplate;

    @Test
    void contextLoads() {

        this.easyRulesTemplate.fire("demo", new MyOrder(true));
        this.easyRulesTemplate.fire("demo", new MyOrder(false));
    }

}
