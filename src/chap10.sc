//abstract classes


abstract class Element {
  //abstract method, no body
  //class itself must be declared abstract too
  //no point instantiating class with even a single abstract method
  //abstract keyword not necessary on abstract method
  def contents: Array[String]

  //parameterless methods
  def height: Int = contents.length
  def width: Int = if(height == 0) 0 else contents(0).length

  //methods with empty () are called empty-paren methods
  //convention:
  //use parameterless method when mutable state is not changed (ie a getter)
  //this allows you to change from method to field without affecting the client code
  //fields require more memory whereas methods are slower to call
  //use parenthesis if method performs io, writes vars, or reads vars other than receivers fields, ie has side effects

  //dynamic binding
  def demo() = {
    println("I am in Element now")
  }

  //use final to create a def that cannot be overridden
  final def cantChangeMe() = {
    "can't change me"
  }
}

//extending classes, AnyRef is automatically extended for classes with an extends clause
class ArrayElement(conts: Array[String]) extends Element {
  def contents: Array[String] = conts
  override def demo() = {
    println("hi, ArrayElement here, I extend Element directly")
  }
}



val ee = new ArrayElement(Array("hello", "world"))
//inherits method/fields from the super class Element
ee.width
//as you'd expect, can pass around ArrayElement in super class var type
val supa: Element = new ArrayElement(Array("I'm not me"))

//you can override a parameterless method with a field\
//but you can't define a method and field with the same name in the same class
//like you can in java
class ArrayElementField(conts: Array[String]) extends Element {

  val contents: Array[String] = conts

  override def demo() = {
    println("I am ArrayElementField")
  }
}

//parametric fields
//combine field and parameter in a single definition
class ArrayElementParametricField( val contents: Array[String]) extends Element {
  //empty body
}

//you can use var, val, private, protected, override in parametric fields too
class Cat {
  val dangerous = false
}

//parametric fields with override and private specifier
class Tiger( override val dangerous: Boolean, private var age: Int) extends Cat

//invoke superclass constructor
//note the parameter in the extends section
//also can make whole class final, cannot be subclassed
final class LineElementOld(s: String) extends ArrayElement(Array(s)) {
  override def width = s.length //note: override is required for all concrete members of a parent class, optional if abstract
  override def height = 1

  //can make override in subclass final so nothing further down can override
  final override def demo() = {
    println("magic, I am Line Element, subclass of ArrayElement")
  }
}
//override keyword requirement helps with accidental overrides problem when methods added to base classes

//extending an abstract class, have not reached traits yet
class UniformElement ( ch: Char, override val width: Int, override val height: Int) extends Element {
  private val line = ch.toString * width //note scala style, method looks like operator eg * is a method in StringOps
  def contents = Array.fill(height)(line) //this looks like a curried call, not sure if its required
}


//why to prefer composition over inheritence
//inheritence has the fragile base class issue where you can break subclass
//by changing the superclass
//eg LineElement should really be sublass of Element, not ArrayElement
class LineElement(s: String) extends Element {
  val contents = Array(s)
  override def width = s.length
  override def height: Int = 1

}

//can declare subclasses in variety of ways
val e1: Element = new ArrayElement(Array("hello", "world"))
val ae: ArrayElement = new LineElementOld("hello")
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














