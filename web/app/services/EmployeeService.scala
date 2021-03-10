package services

import models.EmpDetail
import repo.EmployeeRepo

import scala.concurrent.{ExecutionContext, Future}

trait EmployeeService {
  def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]]
}

class EmployeeServiceImpl extends EmployeeService {

  override def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]] = Future {
    List(EmpDetail("Shad", 11, "..."))
  }

}
