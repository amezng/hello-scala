package services

import javax.inject.Inject
import models.EmpDetail
import repo.EmployeeRepo

import scala.concurrent.{ExecutionContext, Future}

trait EmployeeService {
  def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]]

  def addEmployee(emp: EmpDetail)(implicit ec: ExecutionContext): Future[Long]
}

class EmployeeServiceImpl extends EmployeeService {

  override def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]] = Future {
    List(EmpDetail("Shad", 11, "..."))
  }

  override def addEmployee(emp: EmpDetail)(implicit ec: ExecutionContext): Future[Long] = ???

}

class DBEmployeeServiceImpl @Inject()(repo: EmployeeRepo) extends EmployeeService {
  override def addEmployee(emp: EmpDetail)(implicit ec: ExecutionContext): Future[Long] = ???

  override def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]] = repo.listEmployee()
}

class InMemoryEmployeeServiceImpl extends EmployeeService {
  override def listEmployees()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]] = Future {
    List(EmpDetail("From Memory Shad", 101010, "..."))
  }

  override def addEmployee(emp: EmpDetail)(implicit ec: ExecutionContext): Future[Long] = ???
}
