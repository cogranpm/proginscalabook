//chapter 12
//traits
//used for widening thin interfaces to rich ones
//and defining stackable modifications


//super class is AnyRef
trait Philosophical {
  def philosophize() = {
    println("consume therefore am")
  }
}

//you mix traits into a class rather than inherit them
//you implicitly inherit the trait's superclass
class Frog extends Philosophical {
  override def toString() = "green"
}

//mix trait into class that extends a superclass
class Animal
trait HasLegs

class Salamander extends Animal with Philosophical with HasLegs {
  override def toString = "green"

  override def philosophize(): Unit = {
    println("I'm a sillymander")
  }
}

val frog: Philosophical = new Frog()
//i inherit my traits methods, which can be concrete
frog.philosophize()

val sally: Salamander = new Salamander()
sally.philosophize()

//you can do anything in a trait you can do in a regular class, except:
//no class parameters
//super calls are dynamically bound, in classes they are statically bound
//this is all about stackable modifications

//thin vs rich interface
//if you have a rich interface the clients implementing it have to implement many methods
//because traits have built in concrete methods you can add many more methods, the key to this
//is to define the thin interface as a small set of abstract methods the client class
//must implement and a large number of concrete defined in terms of the abstract methods
//example:
class Point(val x: Int, val y: Int) {
  override def toString() = "x = " + x + " y = " + y
}


trait Rectangular {
  //abstract methods, every extender must implement
  def topLeft: Point
  def bottomRight: Point

  //concrete, rich interface
  def left = topLeft.x
  def right = bottomRight.x
  def width = right - left
  //many more geometric methods
}


abstract class Component extends Rectangular {

  //other methods
}

//what i find weird is that the class parameters are vals, and this class does not implement
//the topLeft and bottomRight defs, but all the same the methods are populated in the
//inherited trait
class Rectangle (val topLeft: Point, val bottomRight: Point) extends Rectangular {

  //other methods
}

val rect = new Rectangle(new Point(1, 1), new Point(10, 10))
//this rect instance, has all the concrete methods of trait Rectangular
rect.left
rect.right
rect.width
rect.topLeft

//the ordered trait
//a built in trait that provides comparison for any class as long as you implement the
//compare method
//requires a type parameter
class Rational(n: Int, d: Int) extends Ordered[Rational] {

  val numer: Int = n
  val denom: Int = d

  //must implement compare
  //0 = same, -ve if receiver less than argument, +ve otherwise
  //this is a formula for a rational number
  def compare (that: Rational) =
    (this.numer * that.denom) - (that.numer * this.denom)

  //the Ordered trait gives you all these:
  //< > <= >=
  //note that these methods are all implemented in terms of the abstract method compare
  //so this is a perfect example of rich interface concept, all the operations call
  //compare to calculate their results
  //it does NOT give you equals, which you need to define yourself
  //note: equals has a particular signature, the argument is of type Any
  override def equals(that: Any): Boolean =
    that match {
      case that: Rational => that.numer == this.numer && that.denom == this.denom
      case _ => false
    }
}

//traits as stackable modifications
//modify means a trait can alter that data of a class for example, by doubling all items in a list

abstract class IntQueue{
  def get(): Int
  def put(x: Int)
}

import scala.collection.mutable.ArrayBuffer

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]
  def get() = buf.remove(0)
  def put(x: Int) = {buf += x}
}

val queue: BasicIntQueue = new BasicIntQueue
queue.put(10)
queue.put(20)
queue.get()
queue.get()

//trait doubles integers as put into queue
//trait extends a class, can only be mixed into class that also extends IntQueue
trait Doubling extends IntQueue {

  //super call on a method declared abstract
  //the trait needs to be mixed in AFTER another trait or class that gives a concrete definition to the method
  //mark method as abstract override to get this kind of behaviour
  abstract override def put(x: Int) = {
    super.put(2 * x)
  }
}

class MyQueue extends BasicIntQueue with Doubling

val queueD = new MyQueue
//the standard put method is actually altered by the trait
queueD.put(10)
queueD.get()

//stacking modify traits
trait Incrementing extends IntQueue {
  abstract override def put(x: Int) = {super.put(x + 1)}
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int) = {
    if(x >= 0) super.put(x)
  }
}

//check this out, apply traits in the instantiation of an object, crazy in a good way
//this means you could alter behaviour of library classes
val queueStakka = (new BasicIntQueue with Incrementing with Filtering)
//the order of mixins is significant, trait furthest to right is called first, ie Filtering in this case, then it goes leftward
queueStakka.put(-1)
queueStakka.put(0)
queueStakka.put(1)
queueStakka.get()
queueStakka.get()


//trait vs abstract class
//if behaviour not reused, make a class
//if might be reused in multiple unrelated classes, make a trait
//if inheriting from java code, use abstract class
//if distributing in compiled form, might use an abstract class, when a trait gains or loses a member, clients must recompile
//NOte, the jvm loads dependent library classes at runtime, only at that point will you know about an incompatibility
//binary compatibility is a complex beast: see https://docs.scala-lang.org/overviews/core/binary-compatibility-for-library-authors.html





