package org.easyrules.samples.fizzbuzz


 class FizzBuzz { 
   static void main(String... args) {

        def label = 'FIZZBUZZ WITH CODE'.replaceAll(/./){it+' '}
        def width = 80

        println """${'='*width}
                  |${label.center width }
                  |${'='*width}""".stripMargin()

  	    // Ultra compact, show-off, version in 59 characters
        // 1.upto(100){println((it%3?'':'Fizz')+(it%5?'':'Buzz')?:it)}


        // Easier to follow
        (1..100).each {
              def string = ''
              if (it%3 == 0) {string = 'Fizz'}
              if (it%5 == 0) {string += 'Buzz'}
              println string ? string : it
         }

  }
}

