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
class Frog extends Philosophical {
  override def toString() = "green"
}


