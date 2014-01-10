
package d2e



import scala.concurrent._
import ExecutionContext.Implicits.global

object FutureComposition extends App {

    val f: Future[Int] = future {
        Thread.sleep(5000); println("here"); 40
    }
    val f2: Future[Int] = future {
        println("There"); 5
    }

    def doCompare(i: Int, j: Int) = { println("comparing: " + i + ", " + j); i > j }

    val compo = for {
        fv <- f
        fv2 <- f2
        if doCompare(fv, fv2)
    } yield (fv, fv2)

    compo onSuccess {
        case x => println("S: " + x)
    }

    compo onFailure {
        case x => println("F: " + x)
    }

    Thread.sleep(10000)

}
