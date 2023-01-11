package com.balancefriends.rules.jmh;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.Throughput)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Benchmarks {
  private  Rules rules;
  private static int NO_OF_REQUESTS = 50;
  private static int NO_OF_RULES = 50;
  private RulesEngine rulesEngine;
  @Setup
  public void setup() throws IOException {
    System.out.println("---------- SETUP ONCE -------------");
    rulesEngine = new DefaultRulesEngine();
    rules = new Rules();
    for (int i = 0; i < NO_OF_RULES; i++) {
      String condition = "easy/v"+i+"/api";
      String action = "Execute for rule" + i;
      Rule rule = new RuleBuilder()
          .name("weather rule" + i)
          .priority(NO_OF_RULES - i)
          .description("if it rains then take an umbrella")
          .when(facts -> facts.get("proxyUrl").equals(condition))
          .then(facts -> System.out.println(action))
          .build();
      rules.register(rule);
    }
  }

  @TearDown
  public void tearDown() throws InterruptedException {
    System.out.println("---------- TEAR DOWN ONCE -------------");
  }

  @Benchmark
  public void fire(Blackhole blackhole) throws InterruptedException {
    Facts facts = new Facts();

    facts.put("proxyUrl", "easy/v7/api");
    rulesEngine.fire(rules, facts);
  }

}
