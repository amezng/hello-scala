package controllers

import play.api.mvc.InjectedController

class WelcomeController extends InjectedController {

  def hello = Action { implicit request =>
    Ok("Welcome to play!")
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

}
