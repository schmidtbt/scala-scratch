/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



import akka.actor.{ActorSystem, Props}
import akka.camel.{CamelMessage, Consumer}

object AkkaCamelFailureModes extends App {

    val system = ActorSystem.create("sys")
    system.actorOf(Props(classOf[CamelTest]), name = "ctest")

}

class CamelTest extends Consumer {
    def endpointUri = "jetty:http://0.0.0.0:8877/?matchOnUriPrefix=true"

    def receive = {
        case msg: CamelMessage =>
            Thread.sleep(70000)
    }
}
