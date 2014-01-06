/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



import org.parboiled.errors.ParsingException
import spray.json.DefaultJsonProtocol._
import spray.json._

object JsonTesting extends App {

    val jsonString = """ {"numbers": 4} """

    val p = jsonString.asJson.asJsObject()
    p.fields.foreach(x => println(x._2.getClass))
    println(p.prettyPrint)

    // Create simplified ways to generate the Js* structures without having to instantiate Js* objects
    implicit def StringToJsString(str: String) = {
        JsString(str)
    }
    implicit def StringToJsNumber(num: Int) = JsNumber(num)

    // Create nested structures
    val x = Map[String, JsValue]("a" -> 5,
        "d" -> "whatever",
        "q" -> JsArray(List(JsNumber(3), JsNumber(5))),
        "d" -> JsObject(Map[String, JsValue]("r" -> JsNumber(4), "e" -> JsNumber(5)))
    ).toJson.prettyPrint

    println(x)



    println("-----------")
    println("-----------")

    val x1 = """ {"key1": "value1", "key2": 4} """
    println(x1.asJson)
    println(x1.asJson.convertTo[Map[String, JsValue]])

    val m = x1.asJson.convertTo[Map[String, JsValue]]
    val z = m.map({
        case (x, y) => {
            val ny = y.toString(x => x match {
                case v: JsString =>
                    v.toString().tail.init
                case v =>
                    v.toString()
            })
            (x, ny)
        }
    })

    println(z)


    println("-----------")
    println("-----------")

    val mm = Map[String, JsValue]("val" -> JsString("key"))
    val value = JsObject(mm).compactPrint
    println(value)






    println("-----------")
    println("-----------")

    // basic example with iterating over each of the map values and conversion to a Map[String, JsValue]
    val s1 = """ {"numbers": 4, "val": "r", "asdf": { "toys": 9} } """.asJson.convertTo[Map[String, JsValue]]
    println(s1)
    s1.foreach({
        case (x, y) => println(y.getClass)
    })
    println(s1.getOrElse("numbers", JsNumber(2)).convertTo[Int] + 3)

    // Example parsing to optional values to test conformity perhaps?
    val s2 = """ {"address": "reaxt://chatrelay:message@engine", "token": "asdfkjasfelkajsef", "asdf": { "toys": 9} } """.asJson.convertTo[Map[String, JsValue]]
    println(s2)
    s2.foreach({
        case (x, y) => println(y.getClass)
    })
    println(s2.getOrElse("address", JsString("none")).convertTo[String])
    println(s2.getOrElse("token", JsString("none")).convertTo[Option[String]])
    println(s2.getOrElse("missingentry", JsNull).convertTo[Option[String]])


    // Example parsing bad JSON structures and catching error
    try {
        val s3 = """ {"} """.asJson.convertTo[Map[String, JsValue]]
        println(s3)
        s3.foreach({
            case (x, y) => println(y.getClass)
        })
    } catch {
        case e: ParsingException =>
            println("not good json")
    }


    println("-----------")
    println("-----------")


}
