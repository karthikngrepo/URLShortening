package dev.ng.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class URLShorteningRequest(clientId: String, secret: String, longUrl: String)

case class URLShorteningResponse(statusCode: String, shortUrl: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val requestFormat: RootJsonFormat[URLShorteningRequest] = jsonFormat3(URLShorteningRequest)
  implicit val responseFormat: RootJsonFormat[URLShorteningResponse] = jsonFormat2(URLShorteningResponse)
}