package repo

import models.EmpDetail

import scala.concurrent.{ExecutionContext, Future, blocking}
import scalikejdbc._

trait EmployeeRepo {

  def listEmployee()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]]

}

class PostgresEmployeeRepoImpl extends EmployeeRepo {

  override def listEmployee()(implicit ec: ExecutionContext): Future[Seq[EmpDetail]] = Future {
    blocking {
      DB readOnly { implicit session =>
        sql"""select name, salary, address from emp"""
          .map(r => EmpDetail(r.string("name"), r.int("salary"), r.string("address")))
          .list().apply()

      }
    }
  }

}
