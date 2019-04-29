//lists
//literals
val fruit = List("apples", "oranges", "pears")
val nums = List(1, 2, 3, 4)
val diag3 =
  List(
    List(1, 0, 0),
    List(0, 1, 0),
    List(0, 0, 1)
  )
val empty = List()

//lists are immutable, arrays are not
//its a linked list implementation, recursive access good, random access bad
//elements all of a single type
//lists are covariant, meaning List[S] is a subtype of List[T] if S extends T

//create an empty list of particular type, the type Nothing is a subtype of every other type
val xs: List[String] = List()

//constructing lists
//Nil and :: (cons)
//in raw form:
val fruitRaw = "apples" :: ("oranges" :: ("pears" :: Nil))
//this is lispy type stuff, read :: as an infix operator, on the left is item to add to list followed by
//the list elements, hence the params, which as we find can be removed altogether
//:: ends in a colon so it reads to the right
val numsRaw = 1 :: 2 :: 3 :: 4 :: Nil

//basic operations
//head, tail (exclude first), isEmpty
//head and tail will throw for empty list

//example of list processing, insertion sort

def insert(x: Int, xs: List[Int]): List[Int] =
  if(xs.isEmpty || x <= xs.head) x :: xs
  else xs.head :: insert(x, xs.tail)

def isort(xs: List[Int]) : List[Int] =
  if (xs.isEmpty) Nil
  else insert(xs.head, isort(xs.tail))

val existingList = List(1, 2, 3, 5, 6)
insert(4, existingList)
val unsortedList = List(2, 5, 4, 6, 3)
insert(1, isort(unsortedList))

//list patterns
val List(a, b, c) = fruit
//matches lists of length 3, if you don't know the number of elements match with ::
val e :: f :: rest = fruit
println("Matching results on e :: f :: rest ")
println(s"e: $e")
println(s"f: $f")
println(s"the rest should be a list: $rest")

//pattern matching example of sorting
def insertpt(x: Int, xs: List[Int]): List[Int] = xs match {
  case List() => List(x)
  case y :: ys => if( x <= y) x :: xs  else y :: insertpt(x, ys)
}

def isortpt(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case x :: xs1 => insertpt(x, isortpt(xs1))
}



//this call inserts 1 in the correct position in the list
println("sorting a list based on pattern matching and recursion")
println("previous sort relied on head and tail")
println("reminder: head gets the first item, tail excludes the first item and gets the rest")
println("here are results of sorting a list pattern match style")
insertpt(1, isortpt(unsortedList))
println("matches are: List() and y :: ys ")
println("List() I think matches the empty list")
println("x :: xs1 breaks down first and remaining items, then recursive call sends in remaining items")



println("forms of pattern matching on lists are List(...) and :: ")
println("which are the extractor pattern and cons pattern x :: xs is treated as ::(x, xs)")
println("a :: b will take apart a list into the first and second items")
println("important thing to remember, :: (cons) means append in normal code, in pattern means break down list")

println("First order methods on class list, means does not take any functions as arguments")
println("::: means concatenate two lists, its infix, method in class list")
List(1, 2) ::: List(3, 4)
println("Associates to the right, ie xs ::: (ys ::: zs), concat xs with the sum of ys, zs")

//concat list done the manual way using patten match, is just an exercise
//use the divide and conquer principle over recursive data structures
//such as lists, we will take apart xs and prepend to ys
def append1[T](xs: List[T], ys: List[T]) : List[T] = ???

//sketch out the pattern match
def append2[T](xs: List[T], ys: List[T]) : List[T] =
  xs match {
    case List() => ??? //this means empty list
    case x :: xs1 => ???
  }

def append3[T](xs: List[T], ys: List[T]) : List[T] =
  xs match {
    case List() => ys //this means empty list, if xs empty just return ys
    case x :: xs1 => ???
  }


def append4[T](xs: List[T], ys: List[T]) : List[T] =
  xs match {
    case List() => ys //this means empty list, if xs empty just return ys
    case x :: xs1 => x :: append4(xs1, ys) //the head is repeatedly concatenated to ys, xs is the tail, ie the remaining list
  }

//lets try it now
append4(List(1,2,3), List(4, 5, 6))

//length is slow on lists, fast on arrays
List(1,2).length
List().isEmpty
//init and last (these are slow, access from the head if possible)
//init gets all elements except the last one and last is obvious
List(1, 2).init //returns a list
List(1, 2).last //returns a list element

//reverse the list if you need to work from the back, its faster
List('a', 'b', 'c', 'd', 'e').reverse
//remember, lists are immutable
//here's how you could reverse a list via pattern matching
def rev[T](xs: List[T]) : List[T] =
  xs match {
    case List() => xs
    case x :: xs1 => rev(xs1) ::: List(x) //keep adding head to the tail, until the passed in becomes empty
  }

//prefixes and suffixes







































































