
package d2e



import sys.process._

object ExtPython extends App {

    Seq("cd", "/").!

    (1 to 10).par.foreach(i => println("/home/revan/temp/sleeper.py".!!))

}

