//assertions and tests

def someCrap(a: Int): Int = {
  assert(a > 5) //failing assert will throw an exception: java.lang.AssertionError and exit
  a
}

def thisMayAssert() = {
 try {
   someCrap(2)
 }
  catch {
    case e: AssertionError => println("assertion error")
    case _ : Throwable => println("this is a catch all")
  }
  println("about to do 6")
  someCrap(6)
}

thisMayAssert()