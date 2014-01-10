
package d2e



import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import java.util.logging.Level
import org.parboiled.errors.ParsingException
import spray.json.DefaultJsonProtocol._
import spray.json._

object MongoTest extends App {

    try {

        // Useful snippet to stop verbose output from Mongo
        import java.util.logging.Logger
        val mongoLogger = Logger.getLogger("com.mongodb")
        mongoLogger.setLevel(Level.OFF)


        val mongoClient = MongoClient("localhost", 27017)
        mongoClient.databaseNames

        val db = mongoClient("decipherio")
        val coll = db("revananubis.othertest")
        coll.drop()
        println(coll)

        val a = MongoDBObject("hello" -> "world")
        val b = MongoDBObject("language" -> "scala")
        val c = MongoDBObject("number" -> 1)

        coll.insert(a)
        coll.insert(b)
        coll.insert(c)

        // Obtain list of JSON conversion via mongo
        val ans = coll.find().map(i => JSON.serialize(i)).toList
        println("---------")
        ans.foreach(println)
        println("---------")
        // Convert mongo to spray-json
        val spray = ans.map(i => i.asJson.asJsObject())
        spray.map(_.convertTo[Map[String, JsValue]]).foreach(println)
        println("---------")

    } catch {

        case e: java.net.ConnectException =>

        case e: Exception =>
            e.printStackTrace()
    }

}
