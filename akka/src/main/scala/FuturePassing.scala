/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



import akka.actor._
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success

object FuturePassing extends App {

    implicit val timeout = akka.util.Timeout(1000)

    val system = ActorSystem.create("conduit")

    val act1 = system.actorOf(Props(classOf[Actor1]), name = "actor1")
    val act3 = system.actorOf(Props(classOf[Actor3]), name = "actor3")
    val act2 = system.actorOf(Props(classOf[Actor2], act3), name = "actor2")

    val f = act2 ? "hiya"

    f.onComplete({
        case Success(x) =>
            println("OS: " + x)
        case Failure(x) =>
            println("OF: " + x)
    })

    Thread.sleep(2000)
    println("----------")

    val f2 = act2 ? 1

    f2.onComplete({
        case Success(x) =>
            println("OS: " + x)
        case Failure(x) =>
            println("OF: " + x)
    })



    Thread.sleep(5000)
    system.shutdown()
    system.awaitTermination()

}

class Actor1 extends Actor with ActorLogging {
    implicit val timeout = akka.util.Timeout(1000)

    def receive = {
        case x =>
            println("3 Got: " + x)
            sender ! x + "1"
    }
}

class Actor2(act3: ActorRef) extends Actor with ActorLogging {
    implicit val timeout = akka.util.Timeout(1000)

    def receive = {
        case x: Int =>
            println("2 Got: " + x)

            act3.tell(x + 1, sender)

        case x: String =>
            println("2 Got: " + x)

            val origin = sender

            val f = act3 ? (x + "2")

            f.onComplete({
                case Success(z) =>
                    println("2S Got: " + z)
                    origin ! z + "S"
                case Failure(z) =>
                    println("2F Got: " + z)
                    origin ! z + "F"
            })
    }
}

class Actor3 extends Actor with ActorLogging {
    implicit val timeout = akka.util.Timeout(1000)

    def receive = {
        case x =>
            println("3 Got: " + x)
            sender ! x + "3"
    }
}
