package com.github.scala

class Machine(cpu: Int, memory: Int) {

  def calculateAndSave(f: Int => Float): Float = {
    val usage = f(cpu)
    //Save the usage value in database
    //return this usage
    usage
  }

  def calculateAndSendJson(f : Int => Float): String = {
    val usage = f(cpu)
    usage.toString
  }

  def maxCpu(xs: List[Float]): List[Float] = {
    xs.takeWhile(p => p < 0.2)
    xs.flatMap(x => Seq(100f, x))
  }


}

//Complementary Object for the class Machine
object Machine {

  //Auxillary constructor
  def apply(cpu: Int): Machine = new Machine(cpu, 10)

  def apply(): Machine = new Machine(16, 24)
}

//Functionality
//1. Calculate the usage
// 2. Save the usage in database