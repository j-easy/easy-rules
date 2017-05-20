package org.easyrules.samples.fire

import org.easyrules.samples.fire.rules.*
import org.easyrules.samples.fire.beans.*


import static RulesEngineBuilder.aNewRulesEngine as EasyRules

class Launcher {

    static void main(String... args) {
        
        def label = 'FIRE ALARM'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()


        // Define some room names
        def names = ['Kitchen', 'Bedroom', 'Office', 'Livingroom']

        // Create the rooms for each name; Install a sprinkler system in each room; Add to the World
        def rooms = [:]
        def theWorld = new TheWorld()     

        names.each { name ->
            rooms[name] = new Room(name)
            theWorld.sprinklers << new Sprinkler(rooms[name])
        }


        // Create the rules engine
        def rulesEngine = EasyRules()
                .named("Fire Alarm Demo")
                .build()

        // Register all of the rules
        rulesEngine.registerRule(new EverythingOKRule(theWorld:theWorld))
        rulesEngine.registerRule(new RaiseAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new CancelAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new ThereIsAnAlarmRule(theWorld:theWorld))
        rulesEngine.registerRule(new TurnSprinklerOnRule(theWorld:theWorld))
        rulesEngine.registerRule(new TurnSprinklerOffRule(theWorld:theWorld))

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
