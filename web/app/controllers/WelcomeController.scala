package controllers

import javax.inject.Inject
import models.{EmpDetail, Person, PersonId}
import play.api.libs.json.{Json, OFormat, Reads}
import play.api.mvc.InjectedController
import services.{EmployeeService, EmployeeServiceImpl}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait EmployeeFormats {
  implicit val employeeFormat = Json.format[EmpDetail]
  implicit val personReads: OFormat[Person] = Json.format[Person]
  implicit val personIdFormat = Json.format[PersonId]
}

/**
 * Annotation - inject, will signal the dependency injection library, Guice to create an
 * object of the employee service trait.
 * @param employeeService
 */
class WelcomeController @Inject()(employeeService: EmployeeService)
    extends InjectedController
    with EmployeeFormats {

  def hello = Action { implicit request =>
    Ok("Welcome to play!")
  }

  /**
    * Process Json request
    * @return
    */
  def postHello = Action(parse.json) { implicit request =>
    val person: Person = request.body.as[Person]

    //example of map key value
    val kv: Map[String, Int] = Map("a" -> 1, "c" -> 2, "e" -> 3)

    val personMap: Map[PersonId, Person] =
      Map(PersonId(1, "45676545753") -> person)
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
