//这行代码虽然在IDE里面报红色错误，并且sbt命令行编译失败，但实际上在sbt命令行执行 show analytics/*:libraryDependencies 会发现已经正常工作了。2020-01-12 08:53:39
/**
 *  error: not found: value BuildUtils
 *libraryDependencies ++= BuildUtils.loggingDependencies()
 *                       ^
 */
libraryDependencies ++= BuildUtils.loggingDependencies()

/**
 * start 2020-01-14 14:30:35
 */
import com.typesafe.sbt.SbtNativePackager._
import NativePackagerKeys._

//-----------
// Packaging
//-----------
mainClass := Some("org.moly.Analytics")
packageArchetype.java_server


bashScriptExtraDefines += """addJava "-
         Danalytics.properties=${app_home}/../conf/analytics.properties""""
batScriptExtraDefines += """set _JAVA_OPTS=%_JAVA_OPTS% -
         Danalytics.properties=%ANALYTICS_HOME%\\conf\\analytics.properties"""


/**
 * end 2020-01-14 14:30:35
 */
