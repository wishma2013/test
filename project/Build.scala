import sbt.{ForkOptions, Fork, File, Process => sbtProcess}

/**
 * Created by eilir on 2019/12/29.
 */
trait UberJarRun {
  def start() : Unit
  def stop() : Unit
}

class MyUberJarRunner(uberJar: File) extends UberJarRun{
  var p: Option[sbtProcess] = None
  override def start(): Unit = {
    p = Some(Fork.java.fork(ForkOptions(), Seq("-cp", uberJar.getAbsolutePath, "Main")))
  }

  override def stop(): Unit = {
    p foreach(_.destroy())
  }
}
