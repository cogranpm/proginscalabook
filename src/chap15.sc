//case classes and pattern matching

abstract class Expr
case class Var(name: String) extends Expr
case class Number(num: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

//no need for {} if class is empty

//case class additions:
//factory method:
val v = Var("x")
val op = BinOp("+", Number(1), v)

//auto arguments prefixed with val, so become a field
v.name

//toString, hashCode, equals
println(op)
op.right == Var("x")

//copy method
//uses named and default parameters
op.copy(operator = "-")

//pattern matching
def simplifyTop(expr: Expr): Expr =
expr match {
  case UnOp("-", UnOp("-", e)) => e //double negation
  case BinOp("+", e, Number(0)) => e //adding zero
  case BinOp("*", e, Number(1)) => e //multiply by one
  case _ => expr
}
//not too sure what is doing here, it's matching on constructors and returning one of the parameters
//as the function result, not sure what good that does
simplifyTop(UnOp("-", UnOp("-", Var("x"))))

//each alternative starts with keyword case
//each includes a pattern and one or more expressions which will be evaluated if pattern matches

//constant pattern: "+" or 1, values that are equal to the constant
def describe(x: Any) = x match {
  case 5 => "five"
  case true => "truth"
  case "hello" => "hi"
  case Nil => "the empty list"
  case _ => "something else"
}
//variable pattern: matches every value, just decomposes value into a named variable you can use
//in the expression
//wildcard pattern: matches every value
//constructor pattern: matches case classes, matches what instance creation would look like i guess
//- arguments to the constructor are patterns themselves, eg if you have a parameter that is itself
//an instance of a case class and so on, note you can use wildcards and variables on constructor pattern
//so any value for the argument is matched

//note: Disambiguation on constants and variables in pattern matching
//lowercase is taken to be variable, and upper case is a constant, eg Pi
import math.{E, Pi}
E match {
  case Pi => "stange math? Pi = " + Pi
  case _ => "OK"
}

//if you want a lowercase constant, say because you have a constant in a lowercase variable
//put backticks around it ``, then it won't treat lowercase as a variable name, also if there
//is a prefix say someObject.pi then it's treated as a regular variable, not a pattern variable

//sequence patterns
val alist: List[Int] = List(1, 2, 3)
alist match {
  case List(1, _, _) => "I matched a list starting with 1"
  case _ => "this is not a list starting with 1"
}
alist match {
  case List(1, _*) => "* is the syntax for matching arbitrary length"
  case _ => "nothing to see"
}

//tuple patterns (a, b, c) where a, b, c are variables
def tupleDemo(expr: Any) =
expr match {
  case (a, b, c) => println("matched " + a + b + c)
  case _ =>
}

tupleDemo(("machu", "pichu", "degrado"))

//typed patterns, look just like variable declaration without val or var
def generalSize(x: Any) = x match {
  case s: String => s.length
  case m: Map[_, _] => m.size
  case _ => -1
}

def matchMap(x: Any) = x match {
  case m: Map[a, b] => println("y8kes")
  case _ => println("no match")
}

generalSize("abc")
val map: Map[Int, Char] = Map(1 -> 'a', 2 -> 'b')
generalSize(map)
matchMap(map)


//here is how to typecase in scala
val x: Int = 0
if(x.isInstanceOf[Int]) {
  val s = x.asInstanceOf[Double] //as a note: this is example of method invocation with explicit type parameter
  s
}

//pattern match on arrays, you can't pattern match types on generic types such as maps
def isStringArray(x: Any) = x match {
  case a: Array[String] => "yes"
  case _ => "no"
}

val as = Array("abc")
isStringArray(as)

//variable binding pattern, pattern match as usual, if succeeds, set variable to matched object
//use @ to seperate the variable name and the matched object
val expr1: Expr = UnOp("+", Number(1.0))
expr1 match {
  case UnOp("+", e @ Number(_)) => e
  case _ => Number(-1)
}

//pattern guards, lets you put an if in the pattern
def simplifyAdd(e: Expr) = e match {
  case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2))
  case _ => e
}

//match positive integers
val a: Int = 2
a match {
  case n: Int if 0 < n => true
  case _ => false
}

//strings starting with letter a
val theA: String = "actuary"
theA match {
  case s: String if s(0) == 'a' => "starts with a"
  case _ => "not starting with a"
}

//sealed classes
//why, so all derivations of a base class are known at compile time
//because they are all in the same file as the sealed class
sealed abstract class Sport
case class Footy(name: String) extends Sport
case class Cricket(ovalType: String) extends Sport

//if one sport was left out, compiler is able to warn about it,
//if you want to rid yourself of the warning use the (e: @unchecked) match ... syntax
def describeSports(e: Sport) = e match {
  case Footy(a) => "footy name is: " + a
  case Cricket(_) => "cricket"
}

val footy: Sport = Footy("AFL")
describeSports(footy)


//the option type
//Option type is returned from some scala standard methods, such as Map.get
val capitals = Map("France" -> "Paris", "Japan" -> "Tokyo")
capitals get "France"
//patterns matching used to deconstruct values from optionals
def show(x: Option[String]) = x match {
  case Some(s) => s
  case None => "?"
}

show (capitals get "Japan")
show (capitals get "Australia")

val someValueMayBeNull: Option[String] = None
if (someValueMayBeNull.isEmpty) println ("Empty here")

//note, Some is a case class, this is how you instantiate an Option variable
val greeting: Option[String] = Some("hello world")
//can also do this
val greetingHi: Option[String] = Option("Hi World")
//you can get the value
greeting.get
//safer way
greeting.getOrElse("nuthin, just nuthin")
//options are collections, such as sequence
greeting.foreach(a => println(a))

//note: you should option whenever a value could be null, like c# ? one
case class User (id: Int, firstName: String, gender: Option[String])
//now I can define an instance and indicate no value
val someOneWhoObjects: User = User(0, "Objects", None)
val imOkayWithIt: User = User(1, "OK", Some("m"))

//other places you can use patterns
//variable definitions
val myTuple = (123, "abc")
val (aNumber, aString) = myTuple
//here, two variables are declared and assigned and tuple is deconstructed
println(aNumber)
println(aString)
//you can desconstruct case classes into variables too
val expVari = new BinOp("*", Number(5), Number(1))
val BinOp(daOp, lefty, righty) = expVari
println(daOp)
println(lefty)
println(righty)

//sequence of cases, can be used anywhere a function literal is expected
//this is a lambda in a variable of type Option[Int], not sure the syntax makes sense yet
val withDefault: Option[Int] => Int = {
  case Some(x) => x
  case None => 0
}

withDefault(Some(10))
withDefault(None)

//it states this is useful for the Akka library
//um, you can figure this out!!!
/*
var sum = 0
def receive = {
  case Data(byte) => sum += byte
  case GetCheckSum(requester) =>
    val checksum = ~(sum & 0xFF) + 1
    requester ! checksum
}
 */

//sequence of cases gives partial function
//Note: this stuff is too obscure for me right now
val second: PartialFunction[List[Int], Int] = {
  case x :: y :: _ => y
}

//applying partial function on an argument
second(List(5, 6, 7))

//Patterns in for expressions
//here a map is broken down into a tuple pattern
for((country, city) <- capitals)
  println("The captial of " + country + " is " + city)

//Some(fruit) is the pattern here
val results = List(Some("apple"), None, Some("orange"))
for(Some(fruit) <- results)
  println(fruit)