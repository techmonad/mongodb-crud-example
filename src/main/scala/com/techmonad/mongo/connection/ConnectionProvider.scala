package com.techmonad.mongo.connection

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{DefaultDB, MongoConnection, MongoDriver}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}


private[connection] object ConnectionProvider {

  lazy val db: DefaultDB = {

    val uri: String = "mongodb://127.0.0.1:27017/persondb"
    val dbName: String = "persondb"
    try {
      val driver = MongoDriver()
      val parsedUri = MongoConnection.parseURI(uri)
      parsedUri.map(driver.connection) match {
        case Success(value) =>
          Await.result(value.database(dbName), 10 seconds) //wait for connection
        case Failure(th) =>
          throw th
      }
    } catch {
      case NonFatal(th) =>
        th.printStackTrace()
        throw th
    }
  }

}

trait ConnectionProvider {

  protected val collectionName: String


  protected def collection: BSONCollection =
    ConnectionProvider.db.collection(collectionName)


}
