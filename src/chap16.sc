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

