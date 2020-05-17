package dev.ng

import akka.http.scaladsl.model.{StatusCodes, Uri}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.alpakka.mongodb.scaladsl.MongoSource
import com.mongodb.reactivestreams.client.MongoDatabase
import dev.ng.db.URLDetails

import scala.concurrent.ExecutionContextExecutor

object RestRoutes {

  def getRoute(database: MongoDatabase)(implicit executionContext: ExecutionContextExecutor): Route = {

    path(Remaining) { hashcode =>
      concat(
        get {
          println(s"Got a GET request for hashcode=$hashcode")

          val collectionName = "urldetails"
          val urlDetails = database.getCollection(collectionName, classOf[URLDetails])
          MongoSource(urlDetails.find(classOf[URLDetails]))
              .runWith()

          redirect(
            Uri.apply("https://doc.akka.io/docs/akka-http/current/introduction.html"),
            StatusCodes.PermanentRedirect
          )
        },

        post {
          println(s"Got a POST request for url=")
        }
      )
    }
  }
}

