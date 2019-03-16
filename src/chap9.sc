
import java.io.File
import java.io.PrintWriter



  def plainOldSum(x: Int, y: Int) =
    x + y

  plainOldSum(23, 87)

  //curried version
  def curriedSum(x: Int)(y: Int) = x + y

  curriedSum(23)(88)

  //calls function partially applied with 1 parameter gets back a function value with
  //first parameter baked in, then in second call applys the function value with second parameter
  //here is what it looks like
  def first(x: Int) = (y: Int) => x + y

  val second = first(23)
  second(88)

  //you can get a reference to the "second" function like this, using placeholder syntax
  //this will get you the baked in function value behaviour, ie with 1 param baked in
  val onePlus = curriedSum(1) _
  onePlus(88)

  val twoPlus = curriedSum(2) _
  twoPlus(5)

  //make new control structures, by creating methods that take arguments
  //here is a twice control structure, that repeats an operation two times and returns the result
  def twice(op: Double => Double, x: Double) = op(op(x))

  twice(x => x + 1, 5)
  //or, placeholder
  twice(_ + 1, 5)

  //any time you find a control pattern repeated in code, think about implementing it as a new control structure
  //for example, pattern open resource, operate on it, close


  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)

    }
    finally {
      writer.close()
    }
  }

  withPrintWriter(
    new File("date.txt"), writer => writer.println(new java.util.Date)
  )
//functionish style, the operation is passed into the control structure, build some code around the operation
//this is called the loan pattern, opens a resource and loans it to a function

//weird syntax support to remember, when you're passing in exactly 1 arument, you can use curly braces to surround argument
//instead of parenthesis
println("hello world")
//or
println { "hello, world"}
//this enables client programmers to write function literals between curly braces
//if you have a method with multiple parameters, you can use currying to make it a 1 parameter call
def withPrintWriterCurried(file: File)(op: PrintWriter => Unit) = {
  val writer = new PrintWriter(file)
  try {
    op(writer)
  } finally {
    writer.close()
  }
}
//there are now two parameter lists with one parameter each
val file = new File("date.txt")

withPrintWriterCurried(file) {writer => writer.println(new java.util.Date())}
//or with more lambda ish var name
withPrintWriterCurried(file) {x => x.println(new java.util.Date())}
//note the syntax difference with curly { instead of (
//keep reading for more

//by-name parameters
//consider the following
var assertionsEnabled = false

def myAssert(predicate: () => Boolean) =
if (assertionsEnabled && !predicate())
  throw new AssertionError

//use is a little awkward
myAssert(() => 5 > 3)
//it would be nice to just go 5 > 3
//by name parameter version, note => in parameter, and not () after function value call
def myAssertByNameParam(predicate: => Boolean) =
if(assertionsEnabled && !predicate)
  throw new AssertionError

//use is more native like
myAssertByNameParam(5 > 3)
//note, could have just made the function accept a straight boolean parameter
//subtle difference is that Boolean is evaluated before function call, and function value parameter is evaluated after



