import scala.io.Source
import SystemTime._

object HumanWorld {

  var name = "Shad"



  def main(args: Array[String]): Unit = {
    val content = Source.fromFile(args(0))
    println(content.mkString)
    dayOfWeek()
    getDate()
    time()
    println(SystemTime.getDate())
  }

  def sayHello(name: String): String = {
    val length = name.length
    "hello " + name
  }


}

