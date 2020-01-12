/**
 * Created by eilir on 2020/1/12.
 */
import sbt._

object HelloPlugin extends sbt.AutoPlugin{
  lazy val helloTask = taskKey[Unit]("Print Hello World")

  override def projectSettings = Seq(
    helloTask := println("Hello World")
  )
}
