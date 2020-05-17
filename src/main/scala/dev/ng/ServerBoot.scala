package dev.ng

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.Materializer
import dev.ng.db.{DatabaseConnectionFactory, MongoServerDetails}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object ServerBoot {

  def main(args: Array[String]): Unit = {
    import RestRoutes._
    implicit val system: ActorSystem = ActorSystem()
    implicit val materializer: Materializer = Materializer(system)
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val db = DatabaseConnectionFactory.getDatabaseConnection(
      List(MongoServerDetails("localhost", 27017)),
      name = "urlshortner")

    val bindingFuture = Http().bindAndHandle(getRoute(db), interface = "localhost", port = 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}