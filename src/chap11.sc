//scala's hierarchy

//every class inherits from Any
//also Null and Nothing
//so Any is a superclass of everything and Nothing is a subclass of everything

//Any has these methods
class AnyDemo

val first = new AnyDemo()
val last = new AnyDemo()
first.==(last)
first != last
first equals last
first.##
first.hashCode()
first.toString()

//every object can be compared using == or != etc
//you can override equals which alters != and ==, which are final

//AnyVal, allows the creation of value types
//AnyRef, normal classes
//Built in value classes
//these are all equivalent to java primitives
val byte: Byte = 0
val short: Short = 0
val char: Char = '0'
val int: Int = 0
val long: Long = 0l
val float: Float = 0.3f
val double: Double = 3.3
val boolean: Boolean = false

//Unit is also a built in value class
def noValue(): Unit =
  println("i'm a unit")

//you cannot create instances of the built in Value classes using new
//because they are abstract and final
val noth: Unit = ()

//value classes built in support expected operators and methods eg:
42.toString()
//there are implicit conversions between value classes
//ie when passing Int to a Long parameter
def myLong(aLong: Long): Long =
  aLong

val myInt: Int = 4
myLong(myInt)

//here are some interesting methods on value classes
42 max 43
42 min 43
1 until 5
1 to 5
3.abs
(-3).abs
//these methods are defined in a class, scala.runtime.RichInt, implicit conversion from Int to RichInt
//conversion applied whenever method invoked on Int that is undefined in Int but available in RichInt

//AnyRef is an alias for java.lang.Object, you can use both in scala programs

//auto boxing, in java primitive types are different from objects
//in java is you compare java.lang.Integer instances with the same number in both
//you'll get a false, if you compare primitives you get true
//why, because == means reference equality
//in scala == is designed to be transparent with respect to types representation
//in java for reference types you should always use .equals, not ==


//for scala reference equality, use the eq method defined in AnyRef
//ne for negation
val x = new String("abc")
val y = new String ("abc")
x == y
x eq y
x ne y

//defining your own value classes
//can have only 1 parameter
//only defs inside
class Dollars (val amount: Int) extends AnyVal {
  override def toString: String = "$" + amount
}

class SwissFrancs(val amount: Int) extends AnyVal {
  override def toString: String = amount + "CHF"
}

val money = new Dollars(25)
money.amount

val franc = new SwissFrancs(100000)
francs.toString











