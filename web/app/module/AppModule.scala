package module

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import services.{EmployeeService, EmployeeServiceImpl, InMemoryEmployeeServiceImpl}

class AppModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[EmployeeService].to[EmployeeServiceImpl]
  }

}
