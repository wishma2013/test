import com.typesafe.sbt.SbtGit._

name := "my-first-scala"

version := "1.0"

scalaVersion := "2.12.8"

//libraryDependencies ++= Seq(
//  "com.typesafe.slick" %% "slick" % "2.1.0",
//  "org.slf4j" % "slf4j-nop" % "1.6.4",
//
//  /**
//   * 这个依赖包已经过时了
//   */
////  "org.specs2" % "specs2_2.10" % "1.14" % "test",
//  "org.specs2" %% "specs2-core" % "4.6.0" % "test"
//)

/**
 * 这是一个方法，很想知道圆括号和花括号的分别
 * @param name
 * @return
 * 2019-12-25 22:39:55
 */
def makeNameOfProject(name:String) : Project = (
  Project(name, file(name))
    .settings( Defaults.itSettings : _*) // (1)
    .settings(
    version := "1.0",
    organization := "org.moly",
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "2.1.0",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "org.pegdown" % "pegdown" % "1.0.2" % "test",//generate html test report
      "org.specs2" %% "specs2" % "1.14" % "test",
//      "org.specs2" % "specs2_2.10" % "1.14" % "test",
      /**
       * 测试java 的junit
       * 2019-12-26 16:29:11
       */
      "junit" % "junit" % "4.11" % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test",
       // already added
      //      "org.specs2" %% "specs2-core" % "4.6.0" % "test"
      // add by eilir 2020-01-03 07:30:47
      "org.apache.derby" % "derby" % "10.10.1.1"
    )
    )
)

/**
 * 这是自定义从terminal执行shell命令后返回的输出
 * 在sbt-and-plugins的jar包里面，很多好用的api
 * 2019-12-25 16:23:14
 */
val myownpref = taskKey[String]("myown definition")

myownpref in ThisBuild := Process("ls -al").lines.tail.head

val myprofile = taskKey[Seq[File]]("Makes a version.properties file.")

/**
 * 测试common/test失败
 * we haven’t defined the settings for this project to find specs2.
 * 2019-12-25 21:41:43
 */
lazy val common = (
  makeNameOfProject("common").
  settings(
    /**
     * 在两个路径发现了version.properties文件
     * ./target/scala-2.12/classes/version.properties
     * ./target/scala-2.12/resource_managed/main/version.properties
     * 内容都是"version=drwxr-xr-x   9 eilir  staff   288 Dec 25 21:20 ."
     * 2019-12-25 21:27:25
     */
    myprofile := {
      val profile = new File((resourceManaged in Compile).value, "version.properties")
      val content = "version=%s" format (myownpref.value)
      IO.write(profile, content)
      Seq(profile)
    }
  )

  )

lazy val analytics = (
  makeNameOfProject("analytics").
  dependsOn(common).
  settings()
  )

lazy val website = (
  makeNameOfProject("website").
  dependsOn(common).
  settings()
  )

lazy val scalastylePlugin = (
  makeNameOfProject("scalastyle-plugin").
//    dependsOn(common).
    settings()
  )

/**
 * 这里如果用"+="会报错"sbt.TaskKey[Seq[sbt.File]] cannot be appended to Seq[sbt.Task[Seq[java.io.File]]]"
 * 貌似是类型的问题。网上(https://github.com/sbt/sbt/issues/971)说如果类型不匹配改成"<+="
 * 改完确实不报错了
 * 2019-12-25 21:19:32
 */
//resourceGenerators in Compile <+= myprofile

javaOptions in Test += "-Dspecs2.outDir=target/generated/test-reports"

javaOptions in run += "-Xmx2048m" // we need lots of heap space

javaOptions in Test += "-Djunit.output.file=" + (target.value /
  "generated/junit.html").getAbsolutePath

fork in Test := true

javaOptions in Test += "-Dspecs2.outDir=" + (target.value / "generated/test-reports").getAbsolutePath

//testOptions += Tests.Argument("--run-listener=com.preownedkittens.sbt.JUnitListener")

testOptions += Tests.Argument(TestFrameworks.Specs2, "html")
testOptions += Tests.Argument(TestFrameworks.JUnit, "--run-listener=com.preownedkittens.sbt.JUnitListener")


val dependentJarDirectory = settingKey[File]("location of the unpacked dependent jars")
dependentJarDirectory := target.value / "dependent-jars"

val createDependentJarDirectory = taskKey[File]("create the dependent-jars directory")

createDependentJarDirectory := {
  Process(s"mkdir ${dependentJarDirectory.value}")
    dependentJarDirectory.value
}

//def create(dir: File, buildJar:File) = {
//  val files = (dir ** "*").get.filter(d => d != dir)
//  val filesWithPath = files.map(x => (x, x.relativeTo(dir).get.getPath))
//  sbt.IO.zip(filesWithPath, buildJar)
//}

val createUberJar = taskKey[File]("create jar which we will run")
//createUberJar := {
//  create(dependentJarDirectory.value, target.value / "build.jar");
//  target.value / "build.jar"
//}

val unpackJars = taskKey[Seq[_]]("unpacks a dependent jars into target/dependent-jars")
  unpackJars := {
    val dir = createDependentJarDirectory.value //依赖
    val log = streams.value.log //整合log
    Build.data((dependencyClasspath in Runtime).value).map ( f =>
      unpack(dependentJarDirectory.value, f, log)
    )
  }

//def unpack(target: File, f:File) = {
//  if(f.isDirectory) sbt.IO.copyDirectory(f, target)
//  else sbt.IO.unzip(f, target, filter = unpackFilter(target))
//}
val excludes = List("meta-inf", "license", "paly.plugins", "reference.conf")
def unpackFilter(target: File) = new NameFilter {
  def accept(name: String) = {
    !excludes.exists(x=>name.toLowerCase().startsWith(x)) &&
      !file(target.getAbsolutePath + "/" + name).exists
  }
}

def unpack(target: File, f:File, log: Logger) = {
  log.debug("unpacking " + f.getName) //并没生效 by eilir 2019-12-29 07:07:37
  if (!f.isDirectory) sbt.IO.unzip(f, target, filter = unpackFilter(target))
}

createUberJar := {
  val ignore = unpackJars.value //整合
  create (dependentJarDirectory.value,(classDirectory in Compile).value, target.value / "build.jar");
  target.value / "build.jar"
}
def create(depDir: File, binDir: File, buildJar: File) = {
  def files(dir: File) = {
    val fs = (dir ** "*").get.filter(d => d != dir)
    fs.map(x => (x, x.relativeTo(dir).get.getPath))
  }
  sbt.IO.zip(files(binDir) ++ files(depDir), buildJar)
}


val runUberJar = taskKey[Int]("run the uber jar")
//runUberJar := {
//  val uberJar = createUberJar.value
//  Process("java -cp " + uberJar.getAbsolutePath + " Main") !
//}

/**
 * 优化 2019-12-29 09:47:27
 */
runUberJar := {
  val uberJar = createUberJar.value
  val options = ForkOptions()
  val arguments = Seq("-cp", uberJar.getAbsolutePath, "Main")
  Fork.java(options, arguments) //执行java命令行的封装方法
}


//(test in IntegrationTest) := {
//  val x = (test in Test).value  // Here we run the tests
//  (test in IntegrationTest).value
//}

val uberJarRunner = taskKey[UberJarRun]("run the uber jar")
uberJarRunner := new MyUberJarRunner(createUberJar.value)

val dbQuery = inputKey[Unit]("Runs a query against the database and prints the result")

val queryParser = {
  import complete.DefaultParsers._
  token(any.* map (_.mkString))
}

val dbLocation = settingKey[File]("The location of the testing database.")
dbLocation := target.value / "database"

val dbHelper = taskKey[DatabaseHelper]("A helper to access testing database.")
dbHelper := derby((fullClasspath in Compile).value, dbLocation.value)


dbQuery := {
  val query = queryParser.parsed
  val db = dbHelper.value
  val log = streams.value.log
  db.runQuery(query, log)
}


//2020-01-06 18:31:58
//import com.typesafe.sbt.SbtGit._

git.baseVersion := "0.1"
// Alter the default project setup for git versioning
def PreownedKittenProject(name: String): Project = (
  Project(name, file(name))
    .settings(versionWithGit:_*)
)


