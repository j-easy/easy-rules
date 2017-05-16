package org.easyrules.samples.fire

import static org.easyrules.core.RulesEngineBuilder.aNewRulesEngine

import org.easyrules.api.RulesEngine

class Launcher {

    static void main(String... args) {
        
        def label = 'FIRE ALARM'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()


        // Define some room names
        def names = ['Kitchen', 'Bedroom', 'Office', 'Livingroom']

        // Create rooms for each name; Install a sprinkler in each room; Add to rules engine
        def rooms = new HashMap<String,Room>()
        def sprinklers = []

        names.each { name ->
            def room = new Room(name)
            rooms[name] = room
            sprinklers << new Sprinkler(room)
        }

        def theWorld = new TheWorld(sprinklers)     

        // Create a rules engine
        RulesEngine rulesEngine = aNewRulesEngine()
                .named("Fire Alarm Demo")
                .build()

        // Register rules
        rulesEngine.registerRule(new EverythingOKRule(theWorld:theWorld))
        rulesEngine.registerRule(new RaiseAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new ThereIsAnAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new CancelAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new TurnSprinklerOnRule(theWorld:theWorld))

        // Fire the rules
        rulesEngine.fireRules()

        pause('Start some fires')

        def kitchenFire = new Fire( rooms['Kitchen'] )
        def officeFire = new Fire( rooms['Office'] )

        theWorld.fires << kitchenFire
        theWorld.fires << officeFire

        // Fire the rules
        rulesEngine.fireRules()

        pause('Put out the fires')
        theWorld.fires.remove kitchenFire
        theWorld.fires.remove officeFire

        // Fire the rules
        rulesEngine.fireRules()

    }

    public static void pause(message) {
        println "\n>>>>>> Press enter to '$message' <<<<<<"
        def keyboard = new Scanner(System.in)
        keyboard.nextLine()
    }

}
