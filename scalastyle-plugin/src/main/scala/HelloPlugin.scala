/**
 * Created by eilir on 2020/1/12.
 */
import sbt._
import sbt.Keys._
import complete.DefaultParsers._

object HelloPlugin extends sbt.AutoPlugin{
  lazy val helloTask = taskKey[Unit]("Print Hello World")
  lazy val helloMsg = settingKey[String]("message for hello task")

  override def projectSettings = Seq(
    helloTask := {
//      val args: Seq[String] = spaceDelimited("<arg>").parsed
      val sourceDir = (scalaSource in Compile).value
      streams.value.log.info("Hello " + helloMsg.value + " " + sourceDir.getAbsolutePath)
    },
    helloMsg := "default message"
  )

}
