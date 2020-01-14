name := "scalastyle-plugin"

version := "1.0"

sbtPlugin := true

organization := "org.moly.sbt"

libraryDependencies ++= Seq("org.scalastyle" %% "scalastyle" % "0.5.0")

libraryDependencies <+= (sbtVersion) { sv =>
  "org.scala-sbt" % "scripted-plugin" % sv
}

//ScriptedPlugin.scriptedSettings
//scriptedLaunchOpts <++= version apply { version =>
//  Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version)
//}
//scriptedBufferLog := false