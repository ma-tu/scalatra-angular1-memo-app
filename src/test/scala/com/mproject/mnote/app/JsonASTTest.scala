package com.mproject.mnote.app

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}
import org.json4s.jackson.JsonMethods

/**
 * Created with IntelliJ IDEA.
 * User: matu
 * Date: 2014/11/02
 * Time: 4:17
 */
@RunWith(classOf[JUnitRunner])
class JsonASTTest extends FlatSpec with Matchers {

  val data = """
      {
        "name": "joe",
        "address": {
          "street": "Bulevard",
          "city": "Helsinki"
        },
        "children": [
          {
            "name": "Mary",
            "age": 5
          },
          {
            "name": "Mazy",
            "age": 3
          }
        ]
      }"""
  val json = JsonMethods.parse(data)

  "JSonAST" should "println" in {
    println(json)
    println(println(json \\ "children" \ "name"))
    println(JsonMethods.compact(JsonMethods.render(json \\ "children" \ "name")))
    println(json.values)
  }
}
