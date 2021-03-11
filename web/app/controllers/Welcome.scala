package controllers

import models.EmpDetail
import play.api.libs.json.{Json, OFormat, Reads}
import play.api.mvc.InjectedController
import services.{EmployeeService, EmployeeServiceImpl}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class WelcomeController() extends InjectedController {

  implicit val employeeFormat = Json.format[EmpDetail]
  val employeeService: EmployeeService = new EmployeeServiceImpl()

  def hello = Action { implicit request =>
    Ok("Welcome to play!")
  }

  case class Person(name: String, age: Int, address: String)
  /**
   * Process Json request
   * @return
   */
  def postHello = Action(parse.json){ implicit request =>
    implicit val personReads: OFormat[Person] = Json.format[Person]
    val person: Person = request.body.as[Person]

    //example of map key value
    val kv: Map[String, Int] = Map("a" -> 1,
    "c" -> 2,
    "e" -> 3
    )

    val personMap: Map[String, Person] = Map("person" -> person)
    Ok(Json.toJson(personMap))
  }

  def sayMyName(name: String) = Action {
    Ok(s"Hello $name. Welcome to Gigahex.")
  }

  def noFoundRequest = Action {
    NotFound(s"Nothing found.")
  }

  def forbiddenRequest = Action { implicit request =>
    request.headers.toMap.foreach {
      case (key, values) => println(s"$key -> ${values.mkString(" ")}")
    }
    Forbidden(s"You are not allowed")
  }


  /**
   * Handles the request asynchronously, and calls a service object which returns
   * a future - list of employees. This list of employees is then converted into the Json object using the Writes
   * defined as implicit at the top of the class.
   * @return
   */
  def listEmployees = Action.async { implicit request =>
      employeeService.listEmployees().map(employees => Ok(Json.toJson(employees)))
  }

}
