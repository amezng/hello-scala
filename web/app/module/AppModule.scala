package module

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import repo.{EmployeeRepo, PostgresEmployeeRepoImpl}
import services.{DBEmployeeServiceImpl, EmployeeService, EmployeeServiceImpl, InMemoryEmployeeServiceImpl}

class AppModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[EmployeeService].to[DBEmployeeServiceImpl]
    bind[EmployeeRepo].to[PostgresEmployeeRepoImpl]
  }

}
