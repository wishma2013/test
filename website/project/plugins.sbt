//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")//这个jar包确实找不到，因为已有版本的版本都太旧了
/**
 * 在路径
 * https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/com.eed3si9n/sbt-assembly/scala_2.12/sbt_1.0/
 * 下有0.14.5版本的依赖 2020-01-15 06:57:51
 */
//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")//这个依赖版本太新，代码不能兼容 2020-01-15 07:08:24
/**
 * 在路径
 * https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/com.eed3si9n/sbt-assembly/
 * 下找旧版本的依赖
 *
 *  sbt-plugin-releases路径会自动添加当前scala版本（scala_2.12）和sbt版本（sbt_1.0），无法找到指定路径的依赖 2020-01-15 07:11:41
 *  根据这个版本
 *  https://dl.bintray.com/sbt/sbt-plugin-releases/com.eed3si9n/sbt-assembly/scala_2.10/sbt_0.13/0.11.2/jars/ 2020-01-16 05:31:42
 *
 *  extra的版本指定写法根本没有用，即使在路径/Users/eilir/.sbt/preloaded/com.eed3si9n/sbt-assembly/0.11.2/ivys/ivy.xml搜索jar包仍然在expect当前编译系统的版本
 *   bad scalaVersion found in /Users/eilir/.sbt/preloaded/com.eed3si9n/sbt-assembly/0.11.2/ivys/ivy.xml: expected='2.12' found='2.10';bad sbtVersion
 *   found in /Users/eilir/.sbt/preloaded/com.eed3si9n/sbt-assembly/0.11.2/ivys/ivy.xml: expected='1.0' found='0.13'; 2020-01-16 05:34:20
 *
 *  如果直接下载到路径里面，但是兼容版本号修改成当前的，仍然会报错找不到object AssemblyKeys  2020-01-16 05:50:36
 */

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2" extra ("scalaVersion" -> "2.10", "sbtVersion" ->
  "0.13"))//这种写法不生效，仍然下载https://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/com.eed3si9n/sbt-assembly/scala_2.12/sbt_1.0//0.11.2/ivys/ivy.xml 2020-01-16 05:31:42