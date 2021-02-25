package com.github.scala

object ConfigFileReader {

  def readFromPath(path: String)(implicit f : FileFormat, x: String): String = {

    ???
  }

  def readFromHttp(url: String, format: FileFormat, fileSize: Int): String = ???

}

class FileFormat(f: String)