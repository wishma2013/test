version := "1.0"
organization := "org.moly.sbt"
name := "scalastyle-test"


//plugins.IvyPlugin
//enablePlugins(HelloPlugin)//书上就是这么写的，但是就是编译不通过！2020-01-12 09:49:42
//enablePlugins(ScalaStylePlugin)//最终这个plugin也没有测试成功！2020-01-14 14:14:12
//HelloPlugin.helloMsg := "new message"//书上这里带org.moly.sbt组织名前缀，但是这里如果带上会报错 2020-01-12 17:46:15

//libraryDependencies ++= BuildUtils.loggingDependencies() //使用ScalaStylePlugin需要用到logging依赖 2020-01-13 08:35:13
//enablePlugins(ScalaStylePlugin)