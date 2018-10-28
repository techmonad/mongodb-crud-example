package com.techmonad.mongo.util


import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

object BSONDocumentConverter {

  implicit object PersonReader extends BSONDocumentReader[Person] {
    def read(doc: BSONDocument): Person = {
      val id = doc.getAs[BSONObjectID]("_id").get
      val name = doc.getAs[String]("name").get
      val age = doc.getAs[Int]("age").get
      Person(id, name, age)
    }
  }


  implicit object PersonWriter extends BSONDocumentWriter[Person] {
    def write(p: Person): BSONDocument =
      BSONDocument("age" -> p.age, "name" -> p.name, "_id" -> p.id)
  }


}

case class Person(id: BSONObjectID, name: String, age: Int)