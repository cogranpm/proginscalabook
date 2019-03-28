package printmenu
import bobsdelights.Fruits
//import a method from a package object defined in bobsdelights/package.scala
import bobsdelights.showFruit

object PrintMenu {
  def main(args: Array[String]) = {
    for(fruit <- Fruits.menu) {
      showFruit(fruit)
    }
  }
}