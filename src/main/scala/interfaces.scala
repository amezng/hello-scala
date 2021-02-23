
class Human(val name: String, address: String) extends CanFly with CanEat {
  override def fly: String = "Flying using aeroplane"

  override def eat: String = "eating cooked food"
}

class Bird(val name: String) extends CanFly with CanEat {
  override def fly: String = "Flying using my wings"

  override def eat: String = "eating the worms"
}

class Robot extends CanFly {
  override def fly: String = "Automatically fly"
}

abstract class Person {
  val name: String
  val address: String
}

trait CanFly {
  def fly : String
}

trait CanEat {
  def eat: String
}