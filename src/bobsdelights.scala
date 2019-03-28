package  bobsdelights


  abstract class Fruit(
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


//see file bobsdelights
//single type import
import bobsdelights.Fruit

//on demand import
import bobsdelights._

//java's import of static class fields
import bobsdelights.Fruits._

  object example {
    def showFruit(fruit: Fruit) = {
      import fruit._ //look at this, an import from a parameter, so instead of fruit.name, can use name
      println(name + "s are " + color)
    }

  }

//import just one or more members
import Fruits.{Apple, Orange}

//rename Apple object to McIntosh
import Fruits.{Apple => McIntosh, Orange}

//to avoid naming clashes
import java.sql.{Date => SDate}
import java.{sql => S}

//also the default style is
import Fruits.{_} //means same as import Fruits._

//import all, and rename apple
import Fruits.{Apple => McIntosh, _}

//import all except pear => _ means exclude
import Fruits.{Pear => _,_}


