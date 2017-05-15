# EasyRules-Gradle


## [Easy Rules](http://www.easyrules.org) and [Java/Maven Tutorials](https://github.com/EasyRules/easyrules-tutorials)

### Usage:

NB: Use '--quiet' or '-q' to supress Gradle build output lines

    ./gradlew usage
       Prints following usage to the console

    ./gradlew FizzBuzz
       Baseline FizzBuzz using code.
       Prints the numbers from 1 to 100. But for multiples of 3 print 'Fizz' instead 
       of the number and for the multiples of 5 print 'Buzz'. For numbers which are 
       multiples of both three and five print 'FizzBuzz'.
       
    ./gradlew FizzBuzzER
       FizzBuzz implementation using EasyRules.
       
    ./gradlew Simple
       Very simple EasyRules examples with one, always true, rule.
       
    ./gradlew HelloWorld -q
       Obligatory 'Hello, world' example where the input is evaluated by a rule.
       
    ./gradlew Shop -P person=Tommy -P age=15
       Rule to evaluate drinking age (US 21); Nmae and age can be passed in via the command line
       or system properties; Default is 'Tom' at age '17'.
       
    ./gradlew Scheduling -q
       A rule which implements scheduling; Reports when the time seconds count is even
       
    ./gradlew Spring
       Similiar to 'Simple' but the rule is injected by Spring
       
    ./gradlew clean
     Remove all reports and artifacts from './build'


