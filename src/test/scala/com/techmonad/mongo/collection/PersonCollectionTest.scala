package com.techmonad.mongo.collection

import com.techmonad.mongo.connection.ConnectionProvider
import org.scalatest.FunSuite

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class PersonCollectionTest extends FunSuite {

  val personCollection = new PersonCollection with ConnectionProvider {
    override protected val collectionName: String = "person"
  }

/*
   test("create person") {
      val result = personCollection.create(Person("Bob", 21))
      await(result).ok === true
    }
*/

/*  test("update person") {
    val result = personCollection.update(Person("Bob joy", 21, Some("5bd48a240494719f3f5a854f")))
    await(result).ok === true
  }*/

/*

  test("delete by id"){
    val result = personCollection.delete("5bd48a240494719f3f5a854f")
    await(result).ok === true
  }

*/

  test("find by name"){
    val persons: Future[Person] = personCollection.find("Bob")
    val d: Person = await(persons)
    println(d)
    "" ===  "1"
    d === ""
  }



  def await[T](future: Future[T]): T =
    Await.result(future, 10 seconds)


}
