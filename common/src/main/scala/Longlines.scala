import scala.io.Source

/**
 * Created by eilir on 2019/2/25.
 */
object Longlines {
  def processFile(filename: String, width: Int): Unit = {
    val source = Source.fromFile(filename, "UTF-8")
    for(line <- source.getLines)
      processLine(filename, width, "" + line)

  }

  def processLine(filename: String, width: Int, line: String): Unit ={
    if(line.length > width)
      println(filename + ": " + line.trim)
  }

}
