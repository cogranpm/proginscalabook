//packages and imports
//help you program in a modular style
//place things in packages, make names visible through imports, control visibility of definitions through
//access modifiers
//see package_stuff.scala

//hidden _root_ package
//every package has _root_ has it's parent package, which allows navigating hierarchies
//see Booster1.scala

//imports can occur anywhere, even inside methods
//useful when you use objects as modules
//see bobsdelights.scala

//this is an import of a package
//allows using regex by itself in the code
import java.util.regex

class AStarB {
  val pat = regex.Pattern.compile("a*b")
}

//implicit imports
//java.lang._
//scala._
//Predef._

//so from java.lang:
Thread.MAX_PRIORITY

//from scala
val myList: List[Int] = List(1, 2, 3)

//from Predef
assert(Thread.MAX_PRIORITY > Thread.MIN_PRIORITY)

//note: later imports overshadow the former
//lang and scala both have StringBuilder so scala.StringBuilder is default
val sb: StringBuilder = new StringBuilder("scala")
sb.toString()
sb.getClass

//access modifiers
//private and protected
class Outer {
  class Inner{
    private def f() = { println("f")}
    def g() = {println ("g")}
    class InnerMost {
      f() // OK
    }
  }
  //(new Inner).f() //error, f is in the inner class and is private, different from java
  (new Inner).g()
}

//in scala, protected is only in subclasses, not like java where it is package accessible

//accessing private members of companion classes and objects
class Rocket{
  import Rocket.fuel
  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10
  def chooseStrategy(rocket: Rocket) = {
    if(rocket.canGoHomeAgain)
      goHome()
    else
      pickAStar()
  }
  def goHome() = {}
  def pickAStar() = {}
}

//package object stuff, in files PrintMenu.scala and bobsdelights/package.scala, this is a
//source code naming convention for package objects, PrintMenu is just a runner
