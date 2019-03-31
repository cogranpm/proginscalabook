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

generalSize("abc")
generalSize(Map(1 -> 'a', 2 -> 'b'))

//here is how to typecase in scala
val x: Int = 0
if(x.isInstanceOf[Int]) {
  val s = x.asInstanceOf[Double] //as a note: this is example of method invocation with explicit type parameter
  s
}

