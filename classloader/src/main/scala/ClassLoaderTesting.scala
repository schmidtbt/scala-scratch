
package d2e



import akka.actor.{Props, ActorSystem, Actor}
import java.net.URL
import java.net.URLClassLoader

object ClassLoaderTesting extends App {

    val system = ActorSystem.create("mysys")
    val a1 = system.actorOf(Props[B])

    val cl = classOf[B].getClassLoader
    val ty = cl.loadClass("akka.actor.Actor")

    val loader = new URLClassLoader(Array(new URL("file:///home/revan/Development/decipherio/d2e/out/artifacts/test/qsimple/plugin6.jar")), cl)
    val cla = loader.loadClass("d2e.ESimple")
    val clazz = cla.asInstanceOf[Class[_ <: MyActor]]

    val acte = system.actorOf(Props(clazz))
    Thread.sleep(1000)
    acte ! "hiya"

    println(clazz.getClass)
    println(clazz.getClass.getSuperclass)



    /*
    val inst = clazz.newInstance()
    val method = clazz.getMethod("receive")
    val output = (method.invoke(inst)).asInstanceOf[PartialFunction[Any,Unit]]
    println(output)

    println(inst.getClass)
    */

    clazz.getDeclaredMethods.foreach(println)

    //val method = clazz.getMethod("giveActor")
    //val output = (method.invoke(inst)).asInstanceOf[Class[_ <: Actor]]

    println(clazz.getClass)
    println(clazz.getClass.getSuperclass)

    ///println(output.getClass)
    //println(output.getClass.getSuperclass)

    println(cl)
    println(Actor.getClass.getClassLoader)
    println(cla.getClassLoader)
    println(clazz.getClassLoader)
    //println(inst.getClass.getClassLoader)

    println(clazz.isInstanceOf[Class[_ <: akka.actor.Actor]])
    println(clazz.isInstanceOf[Class[_ <: Int]])
    //println(clazz.isInstanceOf[Class[Int]])
    println(classOf[Actor].isAssignableFrom(clazz))
    println(clazz.isAssignableFrom(classOf[Actor]))
    //println(classOf[Actor].isAssignableFrom(output))
    //println(output.isAssignableFrom(classOf[Actor]))

    println(clazz)

    //val actor = system.actorOf(Props(clazz), "myremoteactor")

    //Thread.sleep(1000)

    //actor ! "hello"

    //Thread.sleep(1000)

    system.shutdown()
    system.awaitTermination()

}

class A

class B extends Actor {
    def receive = { case msg => println(msg) }
}

abstract class Plugin {
    def printme
}

