package com.github.scala

import java.util.concurrent.Executors

import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future, blocking}
import scala.util.{Failure, Success}

object WithFutures {

  def main(args: Array[String]): Unit = {

    Class.forName("org.postgresql.Driver")
    //Generate a collection of connection which has only 1 connection
    ConnectionPool.singleton("jdbc:postgresql://localhost:8032/localgiga", "giga-admin", "pgpass")

    //implicit val session = AutoSession
    implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(3))


    getEmployee(2).map {
      case Some(EmpDetail(name, _, address)) => println(s"Employee name : ${name} living in $address")
      case None => println("No employee found")
    }
  }

  /**
   * A computer has 'N' cpu cores, in a program you can create threads. Each thread is a light weight process
   * than runs on a single cpu core. If you have 2N threads, then threads are going to share the CPU cores.
   * A thread pool is a collection of threads. At any time, there can a pool with a single thread OR
   * infinite thread.
   * An Executor service will use some thread pool to execute the code
   * |
   * |
   * |
   * |  |
   * |  |
   * |  |
   *    |
   *    |
   *    |
   *
   * @param empId
   * @return
   */
  def getEmployee(empId: Long)(implicit ec: ExecutionContext): Future[Option[EmpDetail]] = Future {
    blocking {
      DB readOnly { implicit session =>
        sql"""SELECT name, salary, address FROM emp WHERE id = $empId"""
          .map { result =>
            EmpDetail(name = result.string("name"),
              salary = result.int("salary"),
              address = result.string("address"))
          }.single().apply()
      }

    }
  }

}
