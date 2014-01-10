
package d2e



import akka.actor.{Props, ActorSystem, Actor}
import akka.pattern.ask
import io.decipher.old.utils.TestParams._
import scala.concurrent._

/**
 *
 * Date: 11/20/13
 */
object FutureTest extends App {

    import scala.concurrent.ExecutionContext.Implicits.global

    val system = ActorSystem.create("mysystem")
    val actor = system.actorOf(Props(classOf[DumbFutureActor]))

    Thread.sleep(1000)

    val f: Future[Int] = future {
        4
    }

    f.onSuccess({
        case x => println(x)
    })

    val f2 = actor ? 2

    f2.onSuccess({
        case x => println(x)
    })

    f2.onFailure({
        case x => println("we fucked up: " + x)
            system.stop(actor)
            Thread.sleep(1000)
            actor ! "alive?"
    })

    Thread.sleep(1000)

    //val f3 = runWithTimeout(2000)( FutureTest.stupidWait(10, "other") )

    def runWithTimeout[T](timeoutMs: Long)(f: => T): Option[T] = {
        Await.result(future(f), dur).asInstanceOf[Option[T]]
    }

    def runWithTimeout[T](timeoutMs: Long, default: T)(f: => T): T = {
        runWithTimeout(timeoutMs)(f).getOrElse(default)
    }

    def stupidWait(x: Int, m: String = "") {
        (1 to x).foreach(i => {
            println("waiting..." + i + " " + m)
            Thread.sleep(1000)

            if (i > 6) {
                Thread.currentThread().interrupt()
            }
        })

    }
}

class DumbFutureActor extends Actor {
    def receive = {
        case msg: Int =>
            FutureTest.stupidWait(10)
            sender ! msg
        case msg: String =>
            println("received: " + msg)
    }

}