//abstract classes
object chap10 {

  import Element.elem

  abstract class Element {
    //abstract method, no body
    //class itself must be declared abstract too
    //no point instantiating class with even a single abstract method
    //abstract keyword not necessary on abstract method
    def contents: Array[String]

    //parameterless methods
    def height: Int = contents.length

    def width: Int = if (height == 0) 0 else contents(0).length

    //methods with empty () are called empty-paren methods
    //convention:
    //use parameterless method when mutable state is not changed (ie a getter)
    //this allows you to change from method to field without affecting the client code
    //fields require more memory whereas methods are slower to call
    //use parenthesis if method performs io, writes vars, or reads vars other than receivers fields, ie has side effects


    //default toString implementation
    //note class inherits from AnyRef
    override def toString: String = contents mkString "\n"


    //this is the above and beside stuff
    //it was in Element class in book, I don't see how that could work as
    //ArrayElement is referenced all over the place, how can the base class Element
    //access ArrayElement which is its subclass
    //the answer is to put it inside a singleton object

    //++ operation concatenates two arrays, scala arrays support many more methods than java
    //they are java arrays underneath, scala arrays can be converted into instances
    //of scala.Seq
    def above(that: Element): Element = {
      //below call to widen, it's actually this.widen(that.width)
      //so remember it's a method call on the left operand
      val this1 = this widen that.width //note the operator style method call
      val that1 = that widen this.width
      elem(this1.contents ++ that1.contents) //using factory method style
    }


    //this is an imperative style, note the for loop
    def besideImperativeStyle(that: Element): Element = {
      //allocate an array
      val contents = new Array[String](this.contents.length)
      //note use of "until" in loop, for 0 based sequences
      for (i <- 0 until this.contents.length)
        contents(i) = this.contents(i) + that.contents(i)
      //note using factory method style
      elem(contents)
    }

    //a more functional version
    //zip operator picks corresponding elements in each sequence passed
    //and forms an array of pairs, eg Array((1, "a"), (2, "b"))
    //for loop with a pattern match to deconstruct array item to a tuple
    def beside(that: Element): Element = {
      //note, this is this.heighten(that.height)
      val this1 = this heighten  that.height
      val that1 = that heighten  this.height
      elem(
        for (
          (line1, line2) <- this1.contents zip that1.contents
        ) yield line1 + line2
      )
    }

    def widen(w: Int): Element =
      if (w <= width) this
      else {
        val left = elem(' ', (w - width) / 2, height)
        val right = elem(' ', w - width - left.width, height)
        left beside this beside right
      }

    def heighten(h: Int): Element =
      if (h <= height) this
      else {
        val top = elem(' ', width, (h - height) / 2)
        val bot = elem(' ', width, h - height - top.height)
        top above this above bot
      }

    //dynamic binding
    def demo() = {
      println("I am in Element now")
    }

    //use final to create a def that cannot be overridden
    final def cantChangeMe() = {
      "can't change me"
    }
  }



  //you can use var, val, private, protected, override in parametric fields too
  class Cat {
    val dangerous = false
  }

  //parametric fields with override and private specifier
  class Tiger(override val dangerous: Boolean, private var age: Int) extends Cat






  //defining a factory object
  //how do you decide what to hide from clients and what to expose


  object Element {
    def elem(contents: Array[String]): Element =
      new ArrayElement(contents)

    def elem(chr: Char, width: Int, height: Int): Element =
      new UniformElement(chr, width, height)

    def elem(line: String): Element =
      new LineElement(line)


    //note: you can place classes and singleton objects
    //inside other classes and singleton objects
    //you can make classes private by putting inside a singleton object and
    //making it private

    //why to prefer composition over inheritence
    //inheritence has the fragile base class issue where you can break subclass
    //by changing the superclass
    //eg LineElement should really be sublass of Element, not ArrayElement
    private class LineElement(s: String) extends Element {
      val contents = Array(s)

      override def width = s.length

      override def height: Int = 1

    }

    //override keyword requirement helps with accidental overrides problem when methods added to base classes

    //extending an abstract class, have not reached traits yet
    private class UniformElement(ch: Char, override val width: Int, override val height: Int) extends Element {
      private val line = ch.toString * width //note scala style, method looks like operator eg * is a method in StringOps
      def contents = Array.fill(height)(line) //this looks like a curried call, not sure if its required
    }


    //extending classes, AnyRef is automatically extended for classes with an extends clause
    private class ArrayElement(conts: Array[String]) extends Element {
      def contents: Array[String] = conts

      override def demo() = {
        println("hi, ArrayElement here, I extend Element directly")
      }


    }

    //invoke superclass constructor
    //note the parameter in the extends section
    //also can make whole class final, cannot be subclassed
    private final class LineElementOld(s: String) extends ArrayElement(Array(s)) {
      override def width = s.length //note: override is required for all concrete members of a parent class, optional if abstract
      override def height = 1

      //can make override in subclass final so nothing further down can override
      final override def demo() = {
        println("magic, I am Line Element, subclass of ArrayElement")
      }
    }

      //you can override a parameterless method with a field\
      //but you can't define a method and field with the same name in the same class
      //like you can in java
      private final class ArrayElementField(conts: Array[String]) extends Element {

        val contents: Array[String] = conts

        override def demo() = {
          println("I am ArrayElementField")
        }
      }

      //parametric fields
      //combine field and parameter in a single definition
      private final class ArrayElementParametricField(val contents: Array[String]) extends Element {
        //empty body
      }

      val ee = elem(Array("hello", "world"))
      //inherits method/fields from the super class Element
      ee.width
      //as you'd expect, can pass around ArrayElement in super class var type
      val supa: Element = new ArrayElement(Array("I'm not me"))


      //can declare subclasses in variety of ways - uh oh, had to change this after making the subclasses private
    //see below they are all declared as Element now
      val e1: Element = new ArrayElement(Array("hello", "world"))
      val ae: Element = new LineElementOld("hello")
      val e2: Element = ae
      val e3: Element = new UniformElement('x', 2, 3)

      //dynamic binding; method implementation invoked is determined at runtime
      //based on the class of the object
      def invokeDemo(e: Element) = {
        e.demo()
      }


    invokeDemo(new ArrayElement(Array("hello", "world")))
    invokeDemo(new LineElement("helloworld"))
    invokeDemo(new UniformElement('x', 2, 2))

  }


}


import chap10.Element.elem
import chap10.Element

object Spiral {
  val space = elem(" ")
  val corner = elem("+")
  def spiral(nEdges: Int, direction: Int): Element = {
    if(nEdges == 1)
      elem("+")
    else {
      //recursive call, not tail call optimized
      val sp = spiral(nEdges - 1, (direction + 3) % 4)

      def verticalBar = elem('|', 1, sp.height)

      def horizontalBar = elem('-', sp.width, 1)

      if (direction == 0)
        (corner beside horizontalBar) above (sp beside space)
      else if (direction == 1)
        (sp above space) beside (corner above verticalBar)
      else if (direction == 2)
        (space beside sp) above (horizontalBar beside corner)
      else
        (verticalBar above corner) beside (space above sp)
    }
  }

  def main(args: Array[String]) = {
    val nSides = args(0).toInt
    println(spiral(nSides, 0))
  }
}





