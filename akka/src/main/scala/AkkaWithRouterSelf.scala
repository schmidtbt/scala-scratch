
package d2e



import akka.actor.{ActorLogging, Props, ActorSystem, Actor}
import akka.routing.RoundRobinRouter

object AkkaWithRouterSelf extends App {
    val system = ActorSystem.create("system")
    val actor = system.actorOf(Props(classOf[SelfActor]).withRouter(
        RoundRobinRouter(nrOfInstances = 5)), name = "test")

    Thread.sleep(1000)
    actor ! "hi"

    Thread.sleep(50000)

}

class SelfActor extends Actor with ActorLogging {
    def receive = {
        case x =>
            log.info("Got: " + x)
            log.info("sleeping..")
            Thread.sleep(1000)
            log.info("awake..")
            context.parent ! x // vs `self ! x`
    }
}
