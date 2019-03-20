//packages and imports
//help you program in a modular style
//place things in packages, make names visible through imports, control visibility of definitions through
//access modifiers

//two ways to package
//use package statement at top, places entire file in the package
package who.done.it
class Doctor

//or packaging syntax
package com.we.did.it {

  package main.suspects {

    class Lawyer
  }


}

package bobsrickets {
  package navigation {

    class Navigator {
      //no need to say bobsrickets.navigation.StarMap
      val map = new StarMap
    }

    class StarMap

  }

  class Ship {
    //no need to say bobsrickets.navigation.Navigator
    val nav = new navigation.Navigator
  }

  package fleets {
    class Fleet {
      //no need to say bobsrickets.Ship
      def addShip() = { new Ship}
    }
  }
}

package bobsrockets {
  class Ship
}

package bobsrockets.fleets {



  class Fleet{
    def AddShip() = {new who.done.it.bobsrockets.Ship}
  }
}

package launch {
  class Booster3
}


