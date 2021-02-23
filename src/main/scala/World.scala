import scala.io.Source
import SystemTime._

object HumanWorld {

  var name = "Shad"

  def main(args: Array[String]): Unit = {
    val content = Source.fromFile(args(0))
    println(content.mkString)
//    dayOfWeek()
//    getDate()
//    time()
    val person = new Human("David", "wasseypur")
    println(person.name)
    val crow = new Bird("crow")
    //Using can fly
    println(person.fly)
    println(crow.fly)

    println(s"A person is ${person.eat}")
    println(s"A bird is ${crow.eat}")

    println(SystemTime.getDate())
    //Declare collections -
    //Javascript style = ["1", "2"]
    //Seq("1", "2")
    //IndexedSeq("1", "2")
    //Array(1, 2, 3)
    //List(1, 3, 4)+
    def process(x: Int, y: Int) = x * y

    val thingsWhoEat = Seq(person, crow)

    val numbers = IndexedSeq(1, 2, 3, 4)
    val sum = numbers.foldLeft(0){
      case (sum, current) => sum + current
    }

    println(sum)


    //shortcut to see the type of the variable- CTRL + Shift + P
    val eatings = thingsWhoEat.map(thing => thing.fly)


    val party = new MarriageParty(thingsWhoEat)

  }

  def sayHello(name: String): String = {
    val length = name.length
    "hello " + name
  }

  class Drone(instrument: CanFly, wings: String) {
    def nowFly = instrument.fly
  }


  class MarriageParty(someone: Seq[CanEat])

}

