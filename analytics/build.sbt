//这行代码虽然在IDE里面报红色错误，但实际上在sbt命令行执行 show analytics/*:libraryDependencies 会发现已经正常工作了。2020-01-12 08:53:39
libraryDependencies ++= BuildUtils.loggingDependencies()