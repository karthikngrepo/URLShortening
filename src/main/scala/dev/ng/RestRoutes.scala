package dev.ng

import java.time.LocalDateTime

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{StatusCodes, Uri}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.Materializer
import akka.stream.alpakka.mongodb.javadsl.MongoSink
import akka.stream.alpakka.mongodb.scaladsl.MongoSource
import akka.stream.scaladsl.{Sink, Source}
import com.mongodb.reactivestreams.client.MongoDatabase
import dev.ng.core.HashCodeGenerator
import dev.ng.db.URLDetails
import dev.ng.models.{JsonSupport, URLShorteningRequest}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object RestRoutes extends Directives with JsonSupport {

  def getRoute(database: MongoDatabase)
              (implicit system: ActorSystem,
               aterializer: Materializer,
               executionContext: ExecutionContextExecutor): Route = {

    val collectionName = "urldetails"

    val collection = database.getCollection(collectionName, classOf[URLDetails])

    concat(
      path(Remaining) { hashcode =>
        concat(
          get {
            println(s"Got a GET request for hashcode=$hashcode")
            val urlDetailsFuture: Future[Seq[URLDetails]] =
              MongoSource(collection.find(classOf[URLDetails]))
                .runWith(Sink.seq)

            onComplete(urlDetailsFuture) {
              case Success(x) =>
                println(s"URLDetails=$x")
                redirect(
                  Uri.apply(x.head.lognUrl),
                  StatusCodes.PermanentRedirect
                )
              case Failure(exception) =>
                println(s"Encountered below exception while url redirection $exception")
                complete("ok")
            }
          }
        )
      },

      path("create") {
        concat(
          post {
            entity(as[URLShorteningRequest]) { request =>

              println(s"Got a POST request for url shortening and request=$request")
              Source.single(request)
                .map { req =>
                  val hashCode = HashCodeGenerator.getCode(req.longUrl)
                  println(s"Got the hashCode=$hashCode")
                  URLDetails(hashCode, req.longUrl, LocalDateTime.now)}
                .runWith(MongoSink.insertOne[URLDetails](collection))

              complete("ok")

            }
          }
        )
      }
    )
  }
}
