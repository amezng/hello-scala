package com.github.scala

import scalikejdbc._

import scala.util.{Failure, Success, Try}

//Abstract data type - ADT
sealed trait ExamResult
case class PassResult(maths: Int, physics: Int, biology: Int) extends ExamResult
case class FailResult(subject: String, marks: Int) extends ExamResult

trait Human {
  val name: String
}
case class Male(name: String, worksAt: String) extends Human
case class Female(name: String, isWorking: Boolean) extends Human

object Examination {

  type ExaminationResult = Either[FailResult, PassResult]

  def main(args: Array[String]): Unit = {

    Class.forName("org.postgresql.Driver")
    //Generate a collection of connection which has only 1 connection
    ConnectionPool.singleton("jdbc:postgresql://localhost:8032/localgiga",
                             "giga-admin",
                             "pgpass")

    implicit val session = AutoSession

    val result = giveExam()
    result match {
      case FailResult(subject, marks) =>
        println(s"failed in $subject with $marks marks")
      case PassResult(maths, physics, biology) =>
        println(
          s"score card:  maths - $maths, physics - $physics, biology - $biology")
      case _ => println("Unknown result")
    }

    //Pattern matching on this type
    val result2 = giveExamWithEither()
    result2 match {
      case Left(FailResult(subject, marks)) =>
        println(s"failed in $subject with $marks marks")
      case Right(PassResult(maths, physics, biology)) =>
        println(
          s"score card:  maths - $maths, physics - $physics, biology - $biology")
    }

    val employees = listEmployees()
    employees match {
      case Failure(exception) => println(s"Failed due to : ${exception.getMessage}")
      case Success(emps) => emps.foreach(emp => println(s"${emp.name} has salary of ${emp.salary}"))
    }
  }

  def giveExam(): ExamResult = FailResult("history", 37)

  def giveExamWithEither(): ExaminationResult = Right(PassResult(100, 89, 90))

  def listEmployees()(implicit session: DBSession): Try[Seq[EmpDetail]] = {
    Try {
      sql"""SELECT name, salary, address FROM emp"""
        .map { result =>
          EmpDetail(name = result.string("name"),
                    salary = result.int("salary"),
                    address = result.string("address"))
        }
        .list()
        .apply()
    }
  }

}
