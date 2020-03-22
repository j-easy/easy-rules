package org.jeasy.rules.spel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MySpringAppConfig {

    @Bean("myGreeter")
    public Greeter create() {

        return new Greeter("Bonjour ", "!");
    }


}
