/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



/*
import akka.actor.ActorSystem
import com.typesafe.config._
import io.decipher.core.conduit.util.ModuleConfiguration

object ExtConfigTesting extends App {

    val config = ConfigFactory.load("decipher.conf")

    val system = ActorSystem.create("conduit")

    //val classType = Class.forName(config.getString("modules.mod1.class"))
    //val objName = config.getString("modules.mod1.name")


    val eset = config.getConfig("modules").root()
    val es = eset.entrySet().flatMap({
        case x =>
            x.getValue.asInstanceOf[ConfigObject].toConfig.getConfig("modules").root().entrySet().map({
                case z => (
                    x.getValue.asInstanceOf[ConfigObject].toConfig.getString("name") + "/" + z.getValue.asInstanceOf[ConfigObject].toConfig.getString("name"),
                    z.getValue.asInstanceOf[ConfigObject].toConfig.getString("class"))
            })
    })
    val ee = eset.entrySet().map({
        case x => (
            x.getValue.asInstanceOf[ConfigObject].toConfig.getString("name"),
            x.getValue.asInstanceOf[ConfigObject].toConfig.getString("class"))
    })

    val e = eset.entrySet().flatMap({
        case x if x.getValue.asInstanceOf[ConfigObject].toConfig.hasPath("modules") =>
            x.getValue.asInstanceOf[ConfigObject].toConfig.getConfig("modules").root().entrySet().map({
                case z => (
                    x.getValue.asInstanceOf[ConfigObject].toConfig.getString("name") + "/" + z.getValue.asInstanceOf[ConfigObject].toConfig.getString("name"),
                    z.getValue.asInstanceOf[ConfigObject].toConfig.getString("class"))
            })
    })

    def recurse(conf: ConfigObject, set: scala.collection.mutable.Set[Map[String, String]] = scala.collection.mutable.Set[Map[String, String]](), name: String = ""): scala.collection.mutable.Set[Map[String, String]] = {
        conf.entrySet().map({
            case x =>
                val extendedName = name + "/" + x.getValue.asInstanceOf[ConfigObject].toConfig.getString("name")
                //val tset = scala.collection.mutable.Set[Any]()
                val temp = Map[String, String](extendedName -> x.getValue.asInstanceOf[ConfigObject].toConfig.getString("class"))
                //println("HERE: " + temp)
                //tset += temp
                set += temp
                if (x.getValue.asInstanceOf[ConfigObject].toConfig.hasPath("modules")) {
                    recurse(x.getValue.asInstanceOf[ConfigObject].toConfig.getConfig("modules").root(), set, extendedName)
                }
                //println(set)
                set
        }).flatten
    }

    val entries = recurse(eset).foldLeft(Map.empty[String, String])(_ ++ _)
    entries.get("/conduit") match {
        case Some(x) => println(x)
        case None => println("whoops")
    }

    // regexp to get at this level all children
    println(entries.keys.filter(x => "^/conduit/[a-zA-Z]*[^/]$".r.pattern.matcher(x).matches()))

    // regexp to get full tree down
    println(entries.filter({
        case (x, y) if ("^/conduit.*".r.pattern.matcher(x).matches()) => true
        case other => false
    }))
    //val mod = entries.flatMap({case x => x.map({case (x,y) => (x,y)})})
    //println(mod)


    println(entries)


    println("------")

    println(ModuleConfiguration.getTLModules)
    println(ModuleConfiguration.getChildren("/conduit"))
    println(ModuleConfiguration.getChildren("/conduit/router"))


    //println(eset)
    //println(es)
    //println(ee)
    //println(eset.getClass)

    //system.actorOf(Props(classType), name=objName)

    //println(config.hasPath("myapp1.akka.loglevel"))
    //println(Class.forName(config.getString("set")))

}
*/