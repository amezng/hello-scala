package repo

import models.EmpDetail

import scala.concurrent.Future

trait EmployeeRepo {

  def listEmployee(): Future[Seq[EmpDetail]]

}

class PostgresEmployeeRepoImpl extends EmployeeRepo {

  override def listEmployee(): Future[Seq[EmpDetail]] = ???

}
