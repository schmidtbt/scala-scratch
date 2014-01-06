/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



import akka.actor.{Actor, ActorRef, Props, ActorSystem}
import akka.pattern.ask
import io.decipher.old.utils.TestParams._

object TwoHopFutureTest extends App {

    import scala.concurrent.ExecutionContext.Implicits.global

    val system = ActorSystem.create("mysystem")
    val hop3 = system.actorOf(Props(classOf[Hop3]), "hop1")
    val hop2 = system.actorOf(Props(classOf[Hop2], hop3), "hop2")
    val hop1 = system.actorOf(Props(classOf[Hop1], hop2), "hop3")

    Thread.sleep(1000)

    hop2 ! hop1

    val f = hop1 ? 4
    f.onSuccess {
        case x => println("Main: got back: " + x)
    }
    f.onFailure {
        case x => println("Main: " + x)
    }

    Thread.sleep(3000)

}

class Hop1(hop2: ActorRef) extends Actor {

    import context.dispatcher

    def receive = {
        case msg: Int =>
            println("Hop1: " + msg + ", from: " + sender)
            val f = hop2 ? msg
            val origin = sender
            f.onSuccess({
                case x =>
                    println("1: got back: " + x + ", from: " + sender)
                    origin ! x
            })
            f.onFailure({
                case x => println("1: " + x + ", from: " + sender)
            })
        case msg: String =>
            println("rec2: " + msg)

    }
}

class Hop2(hop3: ActorRef) extends Actor {

    import context.dispatcher

    var hop1: ActorRef = _

    def receive = {
        case msg: Int =>
            println("Hop2: " + msg + ", from: " + sender)
            val f = hop3 ? msg
            val origSender = sender
            f.onSuccess({
                case x =>
                    println("2: got back: " + x + ", from: " + sender)
                    hop1 ! "here"
                    Thread.sleep(1000)
                    origSender ! x
            })
            f.onFailure({
                case x =>
                    println("2: " + x + ", from: " + sender)
            })
        case ref: ActorRef => hop1 = ref
    }
}

class Hop3 extends Actor {
    def receive = {
        case msg: Int =>
            println("Hop3: " + msg + ", from: " + sender)
            sender ! "thanks"
    }
}