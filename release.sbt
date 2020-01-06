//val ReleaseCommand = Command.command("release") {
//  state =>
//    "all test integrationTests" :: "publish" :: state
//}

//commands += ReleaseCommand

//2020-01-06 13:26:07
//import complete._
//import DefaultParsers._
//def releaseParser(state: State): Parser[String] = ID
//def releaseAction(state: State, version: String): State = {
//  "all test integrationTests" :: "publish" :: state
//}
//val ReleaseCommand =
//  Command.apply("release")(releaseParser)(releaseAction)
//
//commands += ReleaseCommand



//2020-01-06 18:29:01
import complete._
import DefaultParsers._
def releaseParser(state: State): Parser[String] = ID
def releaseAction(state: State, version: String): State = {
  "all test integrationTests" :: "publish" :: state
}
val ReleaseCommand =
  Command.apply("release")(releaseParser)(releaseAction)