
package d2e



/**
 *
 * Date: 11/20/13
 */
object SimpleSecurityManager extends App {

    System.setSecurityManager(new SecurityManager)

    println("STARTING...")
    println("SecurityManager: " + System.getSecurityManager)

    val filename = "/home/revan/temp/2kinghenryvi"
    for (line <- scala.io.Source.fromFile(filename).getLines()) {

    }
    println("Opened filed")

    println(System.getProperty("file.encoding"))

}
