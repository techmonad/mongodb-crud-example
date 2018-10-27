package com.techmonad.mongo.util

import com.techmonad.mongo.collection.Person
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter}

object BSONDocumentConverter {

  implicit val PersonWriter = new BSONDocumentWriter[Person] {
    def write(p: Person): BSONDocument =
      BSONDocument("age" -> p.age, "name" -> p.name)
  }

  implicit val PersonReader = new BSONDocumentReader[Person] {
    def read(doc: BSONDocument): Person = {
      println("cxvcxvcxv     " + doc.getAs[String]("_id"))
      try {
        Person(
          doc.getAs[String]("name").get,
          doc.getAs[Int]("age").get,
          doc.getAs[String]("_id")
        )
      }catch {
        case t => t.printStackTrace()
          Person("", 0)
      }
      }
    }


}
