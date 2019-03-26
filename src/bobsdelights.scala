
package  bobsdelights

abstract class Fruit (
                       val name: String,
                       val color: String
                     )

//note, objects with an object, objects can inherit from class
object Fruits {
  object Apple extends Fruit("apple", "red")
  object Orange extends Fruit("orange", "orange")
  object Pear extends Fruit("pear", "yellowish")
  val menu = List(Apple, Orange, Pear)
}

//single type import
import bobsdelights.Fruit

//on demand import
import bobsdelights._

//importing an object and all members
import bobsdelights.Fruits._

def showFruit(fruit: Fruit) = {
  import fruit._
  println((name + "s are " + color))
}
