import sbt._
import sbt.SettingKey //如果这个地方写成 {SettingKey, File}会引用不上！" / "语法会报错！2020-01-13 06:36:53
import sbt.File
import sbt.Keys.baseDirectory
import sbt.Keys.scalaSource
import sbt.Keys.streams
import org.scalastyle._
/**
 * Created by eilir on 2020/1/12.
 * 这一堆import一点提示都没有，也是很烦恼啊！2020-01-13 08:18:59
 */
object ScalaStylePlugin extends sbt.AutoPlugin{
    override def projectSettings = Seq(
      scalastyleConfig := baseDirectory.value / "scalastyle-config.xml",
      scalastyle := {
        val sourceDir = (scalaSource in Compile).value
        val configValue = (scalastyleConfig in Compile).value
        val s = streams.value
        doScalastyle(configValue, sourceDir, s.log)
      }
    )
    lazy val scalastyleConfig = SettingKey[File]("configuration file for scalastyle")
    lazy val scalastyle = taskKey[Unit]("Runs scalastyle.")

    def doScalastyle(config: File, sourceDir: File, logger: Logger): Unit = {
      if (config.exists) {
        val messages = runScalastyle(config, sourceDir)
        val errors = messages.collect { case x: StyleError[_] => 1}.size
        logger.info(errors + " errors found")
      } else {
        sys.error("%s does not exist".format(config))
      }
    }
    private def runScalastyle(config: File, sourceDir: File) = {
      val configuration = ScalastyleConfiguration.readFromXml(config.absolutePath)
      new ScalastyleChecker().checkFiles(configuration, Directory.getFiles(None, List(sourceDir)))
    }
}
