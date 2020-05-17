package dev.ng.db

import com.mongodb.reactivestreams.client.{MongoClients, MongoDatabase}
import com.mongodb.{MongoClientSettings, ServerAddress}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

import scala.collection.JavaConverters._

case class URLDetails(_id: String, lognUrl: String, lastAccessed: String)

case class MongoServerDetails(host: String, port: Int)

object DatabaseConnectionFactory {

  private val codecRegistry: CodecRegistry = fromRegistries(fromProviders(classOf[Number]), DEFAULT_CODEC_REGISTRY)

  def getDatabaseConnection(mongoServerDetails: List[MongoServerDetails],
                            name: String): MongoDatabase = {
    val serverAddress: List[ServerAddress] = mongoServerDetails
      .map(x => new ServerAddress(x.host, x.port))
      .foldLeft(List.empty[ServerAddress])(_ ++ _)

    val settings: MongoClientSettings = MongoClientSettings.builder()
      .applyToClusterSettings(builder =>
        builder.hosts(serverAddress.asJava))
      .build()

    MongoClients.create(settings).getDatabase(name).withCodecRegistry(codecRegistry)
  }
}
