package com.techmonad.mongo.collection

import com.techmonad.mongo.connection.ConnectionProvider
import com.techmonad.mongo.util.BSONDocumentConverter._
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.{BSONBoolean, BSONDocument, BSONDouble, BSONInteger, BSONLong, BSONNull, BSONString, BSONValue}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait PersonCollection {
  self: ConnectionProvider =>

  def create(person: Person): Future[WriteResult] =
    collection.insert(person)


  def update(person: Person): Future[UpdateWriteResult] =
    collection.update(ordered = false)
      .one(
        q = BSONDocument("_id" -> person.id.get),
        u = person,
        upsert = true,
        multi = false
      )


  def delete(id: String): Future[WriteResult] =
    collection.delete(ordered = false)
      .one(
        q = BSONDocument("_id" -> id)
      )


  def find(name: String): Future[Person] =
    collection.find(BSONDocument("age" -> 21)).requireOne

  /*      .cursor[Person]()
        .collect[List]( -1,  Cursor.FailOnError[List[Person]]())*/

}


object PersonCollection extends ConnectionProvider {
  override protected val collectionName: String = "person"
}


case class Person(name: String, age: Int, id: Option[String] = None)

