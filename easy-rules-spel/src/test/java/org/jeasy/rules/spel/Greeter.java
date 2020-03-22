package org.jeasy.rules.spel;

public class Greeter {

    private String prefix, suffix;

    public Greeter(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public void greeting(String who) {
        System.out.println(prefix + who + suffix);
    }

}
