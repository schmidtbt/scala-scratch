/*******************************************************************************
 * Copyright (c) Decipher.io 2013. All rights reserved.
 ******************************************************************************/

package d2e



import akka.actor.{ActorRef, Actor, Props, ActorSystem}
import akka.pattern.ask
import io.decipher.old.utils.TestParams._

object FutureBlockingTest extends App {

    val system = ActorSystem.create("mysystem")
    val actor2 = system.actorOf(Props(classOf[OtherSendingActor]))
    val actor1 = system.actorOf(Props(classOf[DumbOtherFutureActor], actor2))

    Thread.sleep(1000)

    actor1 ! 4
    (1 to 10).foreach(
        x => {
            Thread.sleep(500)
            actor1 ! x.toString
            actor2 ! x.toString
        }
    )



    Thread.sleep(3000)

    actor1 ! 6
    Thread.sleep(500)
    actor1.tell("false thanks", actor2)

}

class DumbOtherFutureActor(act: ActorRef) extends Actor {

    import context.dispatcher

    def receive = {
        case msg: Int =>
            println("forwarding: " + msg)
            val f = act ? msg
            f.onSuccess({
                case x => println("got back: " + x)
            })
            f.onFailure({
                case x => println(x)
            })
        case msg: String =>
            println("rec1: " + msg)
    }

}

class OtherSendingActor extends Actor {
    def receive = {
        case msg: Int =>
            println("OS: " + msg)
            Thread.sleep(2000)
            sender ! "thanks"
        case msg: String =>
            println("rec2: " + msg)

    }
}