import sbt.Keys._
import sbt._


class DerbyDatabaseHelper(cp: Classpath, db: File) extends DatabaseHelper {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  private def withDriver[A](driver: java.sql.Driver => A): A = {
    // THis crazy is so we can load the derby driver from the local
    // dependencies, rather than depend on it by our build directly.
    val jars = cp.files
    val cl = new java.net.URLClassLoader(jars.map(_.toURL).toArray, null)
    val clazz = cl.loadClass(derbyDriverClassname)
    val d: java.sql.Driver = clazz.newInstance().asInstanceOf[java.sql.Driver]
    driver(d)
  }

  private def withConnection[A](user: java.sql.Connection => A): A = {
    // This is responsible for opening a connection and ensure that the derby driver
    // shuts down the database appropriately.
    withDriver { driver =>
      val connection = driver.connect(s"jdbc:derby:${db.getAbsolutePath};create=true", null)
      try user(connection)
      finally {
        connection.close()
        try driver.connect("jdbc:derby:;shutdown=true", null)
        catch {
          case e: java.sql.SQLException => // Should be derby system shutdown!
        }
      }
    }
  }
  
  def tables: List[String] = withConnection { conn =>
    val db = conn.getMetaData
    fromResultSet(db.getTables(null, null, null, null))(_.getString("TABLE_NAME"))
  }
  
  
  
  private def fromResultSet[A](rs: java.sql.ResultSet)(f: java.sql.ResultSet => A): List[A] = try {
    val buf = new collection.mutable.ListBuffer[A]
    while(rs.next) {
      buf append f(rs)
    }
    buf.toList
  } finally rs.close()
  
  def runQuery(sql: String, log: Logger): Unit = withConnection { conn =>
    val stmt = conn.createStatement
    try {
      if(stmt.execute(sql)) try {
        val rs = stmt.getResultSet
        // Now we print the results of the query.
        val md = rs.getMetaData
        
        val consoleWidth = 
          try jline.TerminalFactory.get.getWidth - 10
          catch {
            case e: Exception => 80
          }
        val colWidth = math.max(consoleWidth / (md.getColumnCount + 1), 10)
        log.info("Column width = " + colWidth)
        val colNums = 1 to md.getColumnCount
        // TODO - Better pretty printing
        log.info(s"-=== Query [${sql take (consoleWidth - 30)}...] results ===-")
        def emptyString(len: Int): String = Stream.continually(' ').take(len).mkString
        def ensureSize(in: String): String = 
          if(in.length < colWidth) emptyString(colWidth - in.length) + in
          else (in take (colWidth - 3)) + "..."
        def printRow(printer: Int => String): Unit = {
          // TODO - Limit size of columns...
          val row = colNums map printer map ensureSize mkString "  "
          log.info(row)
        }
        printRow(md.getColumnName)
        log.info(Stream.continually('-').take(consoleWidth).mkString)
        while(rs.next) {
          printRow(rs.getString)
        }
      } finally stmt.getResultSet.close()
    } finally stmt.close()
  }
}

/** Factory for making derby database interface. */
object derby {
  def apply(cp: Classpath, db: File): DatabaseHelper =
    new DerbyDatabaseHelper(cp, db)
}
