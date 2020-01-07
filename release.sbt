//val ReleaseCommand = Command.command("release") {
//  state =>
//    "all test integrationTests" :: "publish" :: state
//}
//
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
//def releaseParser(state: State): Parser[String] = ID
//def releaseAction(state: State, version: String): State = {
//  "all test integrationTests" :: "publish" :: state
//}
//val ReleaseCommand =
//  Command.apply("release")(releaseParser)(releaseAction)


//2020-01-06 21:31:47 integrationTests任务一直没有跑通过
//def releaseParser(state: State): Parser[String] =
//  complete.DefaultParsers.NotSpace

def releaseParser(state: State): Parser[String] = {
  val version = (Digit ~ chars(".0123456789").*) map {
    case (first ,rest) => (first +: rest).mkString
  }

  val complete =
    (chars("v") ~ token(version, "<version number>")) map {
      case (v, num) => v + num
    }

  Space ~> complete

}

//2020-01-07 06:25:06
val checkNoLocalChanges =
  taskKey[Unit]("checks to see if we have local git changes.  Fails if we do.")
checkNoLocalChanges := {
  val dir = baseDirectory.value
  val changes =
    Process("git diff-index --name-only HEAD --", dir) !! streams.value.log
  if(!changes.isEmpty) {
    val changeMsg = changes.split("[\r\n]+").mkString(" - ","\n - ","\n")
    sys.error("Git changes were found: \n" + changeMsg)
  }
}


def releaseAction(state: State, version: String): State = {
//  "all test integrationTests" ::
  "test" ::
  "checkNoLocalChanges" ::
    s"git tag ${version}" ::
    "reload" ::
    "publish" ::
    state }
val ReleaseCommand =
  Command.apply("release")(releaseParser)(releaseAction)


commands += ReleaseCommand

val releaseHelp = Help (
  Seq(
    "release <verison>" -> "Runs the release script for a given version number"
  ),
  Map (
    "release" ->
      """|Runs our release script. This will:
        |1.Run all the tests.
        |2.Tag the git repo with the version number.
        |3.Reload the build with the new version number form the git tag
        |4.publish all the artifacts""".stripMargin
  )
)

val releaseCommand = Command("release", releaseHelp)(releaseParser)(releaseAction)

commands += releaseCommand

