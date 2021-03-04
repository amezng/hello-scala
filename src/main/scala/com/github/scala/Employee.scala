package com.github.scala
import java.util.concurrent.Executors

import scalikejdbc._

import scala.concurrent.ExecutionContext
import scala.util.Try


class SimpleEmployee(val name: String, salary: Int, address: String){
  override def toString: String = s"$name has salary of $salary INR and lives in $address"
}
object Employee {

  def main(args: Array[String]): Unit = {
    val simpleEmp = new SimpleEmployee("shadab", 1000, "dhanbad")
    println(simpleEmp.name)

     val empObj = EmpDetail("shad", 100, "wasseypur")
    println("Printing the case emp obj")
    println(empObj.name)

    Class.forName("org.postgresql.Driver")
    //Generate a collection of connection which has only 1 connection
    ConnectionPool.singleton("jdbc:postgresql://localhost:8032/localgiga", "giga-admin", "pgpass")

    implicit val session = AutoSession
    val empId = addEmployee(empObj)
    println(s"EmpID : $empId generated")

    val getEmp = getEmployee(2)
    getEmp match {
      case Some(EmpDetail(name, _, address)) => println(s"Employee name : ${name} living in $address")
      case None => println("No employee found")
    }
  }

  def addEmployee(emp: EmpDetail)(implicit session: DBSession): Long = {
    //Query will need a connection and a session. It will login using the above connection, execute the query and will logout
    sql"""INSERT INTO emp(name, salary, address) VALUES(${emp.name}, ${emp.salary}, ${emp.address})"""
        .updateAndReturnGeneratedKey().apply()
  }

  def getEmployee(empId: Long)(implicit session: DBSession): Option[EmpDetail] = {
    sql"""SELECT name, salary, address FROM emp WHERE id = $empId"""
        .map { result =>
          EmpDetail(name = result.string("name"),
          salary = result.int("salary"),
          address = result.string("address"))
        }.single().apply()

  }


  def divideBy(num: Int): Option[Float] = {
    if(num == 0){
      None
    } else {
      Some(100/num)
    }
  }

//  trait Opt
//  object None extends Opt
//  class Some extends Opt
  // 2 + 2 = 4
  // 100 / x = Float or infinity

}

/**
 * 1. Case class don't need to have 'new' keyword when creating object
 * 2. Has toString method implemented by default which shows the value of the data stored
 * 3. By default makes the attributes of the class public and immutable
 * 4. Extract variable names while pattern matching
 * @param name
 * @param salary
 * @param address
 */
case class EmpDetail(name: String, salary: Int, address: String)
