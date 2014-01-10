
package d2e



import akka.actor.Actor.Receive
import akka.actor.ActorDSL._
import akka.actor._
import java.net.{URL, URLClassLoader}
import java.security._

class OurLogger {
    def pout(x: Any) = println("our pout " + x)
}

class BadLogger {
}

object SecondClassLoader extends App {

    Policy.setPolicy(new DynPolicy)
    System.setSecurityManager(new SecurityManager)

    implicit val system = ActorSystem.create("mydynsys")

    (1 to 1).foreach(i => {
        try {
            val loader = new URLClassLoader(Array(new URL("file:///home/revan/Development/decipherio/d2e/out/artifacts/test/tsimple/tsimple.jar")))
            val clazz = loader.loadClass("TSimple")

            val inst = clazz.newInstance()

            clazz.getDeclaredMethods.foreach(println)

            type Logger = {def pout(x: Any): Unit}
            val method = clazz.getMethod("out", classOf[Object])
            val output = (method.invoke(inst, new OurLogger)).asInstanceOf[PartialFunction[Any, Unit]]
            //output.apply(4)

            //println(output)

            //println(Applier.app(output))

            val a = actor(new Act {
                become {
                    actorBehave(sender, self)
                }
            })

            Thread.sleep(1000)
            a ! "hiya"


            /*
            val p = Props(new Actor with ActorLogging {
                def receive = output
            })
            val b = system.actorOf(p, name = "othername")
            */

        } catch {
            case e: Exception =>
                e.printStackTrace()
                println("oops, not there")
        }
        Thread.sleep(2000)

    })

    system.shutdown()
    system.awaitTermination()

    def actorBehave(sender: ActorRef, self: ActorRef): Receive = {
        case x => println(sender); println(self);
    }

}

object Applier {
    def app(x: PartialFunction[Any, Unit]) = {
        x.apply("Something else")
    }
}

class D extends Actor {
    def receive = { case x => println(x) }
}

class DynPolicy extends Policy {

    override def getPermissions(codesource: CodeSource): PermissionCollection = {
        val p = new Permissions
        if (codesource.getLocation.toString.endsWith("/psimple.jar")) {

        } else {
            p.add(new AllPermission())
        }
        p
    }

    override def refresh() = ()
}



/*


simple.jar:

class Simple {
    def out = (x: Int) => x * 4
}


nsimple.jar:

class NSimple {
    def out: PartialFunction[Any,Unit] = {case x => Other.pout(x) }
}

object Other {
    def pout(x: Any) = { println("over here"); println(x) }
}

*/