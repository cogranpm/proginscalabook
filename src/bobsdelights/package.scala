//package object example
//package objects frequently used to hold package-wide type aliases and implicit conversions
//are compiled to class files named package.class that are located in the directory of the package they augment, so
//ususally you'd put the surce file of the package object bobsdelights into a file named package.scala that resides in
//bobsdelights directory
package object bobsdelights {
  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are " + color)
  }
}
