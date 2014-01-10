
package d2e



import net.liftweb.json._
import net.liftweb.json.JsonDSL._
import net.liftweb.json.JsonAST.JValue

object LiftJson extends App {

    val json =
        ("person" ->
            ("name" -> "Joe") ~
                ("age" -> 35) ~
                ("spouse" ->
                    ("person" ->
                        ("name" -> "Marilyn") ~
                            ("age" -> 33)
                        )
                    )
            )

    println(json)
    println(json \\ "spouse")
    println(render(json \\ "spouse"))
    println(compact(render(json \ "person" \ "name")))

    val s4 = """ {"address": "reaxt://chatrelay:message@engine", "token": "asdfkjasfelkajsef", "asdf": { "toys": 9} } """
    val ss4 = parse(s4)
    println(ss4)
    val addr = ss4 \ "address"
    println(addr)
    println(compact(render(addr)))
    println(ss4 \ "other")
    println(ss4 \ "address")
    val JString(x) = ss4 \ "address"
    println(x)

    println(parse(""))

}
