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

    //For comprehension to work with futures...
    val employeesWithSalaryEqualToEmpNo2 = for {
      mayBeEmp <- getEmployee(2)
      employees <- mayBeEmp match {
        case Some(emp) => getEmployeeWithSalary(emp.salary)
        case None => Future.successful(List.empty[EmpDetail])
      }
    } yield employees


    val returnedValue = getName(100)
    returnedValue.flatMap(v => getAddress(v))


    //Monads in functional programming
    //Run the process by composing monadic functions. Monadic functions which return same monad type
    val personAddress = for {
      name <- getName(100)
      address <- getAddress(name)
    } yield address

    //Handle the result using pattern matching
    personAddress match {
      case Some(value) => println(s"Address is : $value")
      case None => println("No address found")
    }
    // a + b = c . this works for all integers
    // 1 + 2 = 3 .
    // [a] + b = c, where 'a' comes from a database
    // [1] + 2 = [3], where '1' comes from a database
  }

  //Make a bigger function by composing smaller functions
  def getAddressById(id: Long): Option[String] = for {
    name <- getName(id)
    address <- getAddress(name)
  } yield address


  //getName is a monadic function
  def getName(id: Long): Option[String] = ???

  //getAddress is a monadic function
  def getAddress(personName: String): Option[String] = ???

  /**
   * A computer has 'N' cpu cores, in a program you can create threads. Each thread is a light weight process
   * than runs on a single cpu core. If you have 2N threads, then threads are going to share the CPU cores.
   * A thread pool is a collection of threads. At any time, there can a pool with a single thread OR
   * infinite thread.
   * An Executor service will use some thread pool to execute the code
   * |[localhost]
   * |
   * |
   * |  |[localhost]
   *    |[Thread state - [[EmpDetail]]   ]
   * |  |----------------------> [db.gigahex.com]
   * |  |                       |
   *    |                       |
   *    |                       |
   *    |                       |
   *    |   result received
   *    | <----------------------
   *
   * @param empId
   * @return
   */
  def getEmployee(empId: Long)(implicit ec: ExecutionContext): Future[Option[EmpDetail]] = Future {
    blocking {
      DB readOnly { implicit session =>
        sql"""SELECT name, salary, address FROM emp WHERE id = $empId"""
          .map { result =>
            EmpDetail(
              name = result.string("name"),
              salary = result.int("salary"),
              address = result.string("address")
            )
          }.single().apply()
      }

    }
  }.recover {
    case e: Exception => None
  }

  def getEmployeeWithSalary(salary: Int)(implicit ec: ExecutionContext): Future[List[EmpDetail]] = Future {
    blocking {
      DB readOnly { implicit session =>
        sql"""SELECT name, salary, address FROM emp WHERE salary = $salary"""
          .map { result =>
            EmpDetail(
              name = result.string("name"),
              salary = result.int("salary"),
              address = result.string("address")
            )
          }.list().apply()
      }

    }
  }.recover {
    case e: Exception => List.empty
  }


}
