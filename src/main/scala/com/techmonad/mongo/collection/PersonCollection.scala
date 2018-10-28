package com.techmonad.mongo.collection

import com.techmonad.mongo.connection.ConnectionProvider
import com.techmonad.mongo.util.BSONDocumentConverter._
import com.techmonad.mongo.util.Person
import reactivemongo.api.commands.{UpdateWriteResult, WriteResult}
import reactivemongo.bson.{BSONDocument, BSONObjectID}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


trait PersonCollection {
  self: ConnectionProvider =>


  def create(person: Person): Future[WriteResult] =
    collection.insert(person)


  def update(person: Person): Future[UpdateWriteResult] =
    collection.update(ordered = false)
      .one(
        q = BSONDocument("_id" -> person.id),
        u = person,
        upsert = true,
        multi = false
      )


  def delete(id: BSONObjectID): Future[WriteResult] =
    collection.delete(ordered = false)
      .one(
        q = BSONDocument("_id" -> id)
      )


  def findById(id: BSONObjectID): Future[Person] =
    collection.find(BSONDocument("_id" -> id)).requireOne



}


object PersonCollection extends ConnectionProvider {
  override protected val collectionName: String = "person"
}


