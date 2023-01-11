package com.balancefriends.rules.jmh;

import java.io.IOException;
import org.openjdk.jmh.runner.RunnerException;

/**
 * The main entry point for running the Reactive-gRPC performance test suite.
 */
public class JMHRunner {
  private JMHRunner() {
  }

  public static void main(String... args) throws IOException, RunnerException {
    org.openjdk.jmh.Main.main(args);
  }
}
