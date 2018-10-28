package com.techmonad.mongo.collection

import com.techmonad.mongo.connection.ConnectionProvider
import com.techmonad.mongo.util.Person
import org.scalatest.{Matchers, WordSpec}
import reactivemongo.bson.BSONObjectID

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class PersonCollectionTest extends WordSpec with Matchers {

  val personCollection = new PersonCollection with ConnectionProvider {
    override protected val collectionName: String = "person"
  }

  "person collection " should {

    val id = BSONObjectID.generate()
    "create person" in {
      val result = personCollection.create(Person(id, "Bob", 21))
      await(result).ok shouldBe true

    }

    "update person" in {
      val result = personCollection.update(Person(id, "Bob joy", 21))
      await(result).ok shouldBe true
    }

    "delete by id" in {
      val result = personCollection.delete(id)
      await(result).ok shouldBe true
    }

    " find  person by name" in {
      val personFuture: Future[Person] = personCollection.findById(BSONObjectID.parse("5bd4980c0494719f3f5a8550").get)
      val person: Person = await(personFuture)
      person.name shouldBe "Bob"
      person.age shouldBe 21
    }

  }

  def await[T](future: Future[T]): T =
    Await.result(future, 10 seconds)

}
