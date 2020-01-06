import sbt.Logger

/**
 * Created by eilir on 2019/12/29.
 */

trait DatabaseHelper2 {
  def runQuery(sql: String, log: Logger): Unit
  def tables: List[String]
}

