package tt.game.m2m.util

import scalikejdbc._
import tt.game.m2m.model.Player

trait Database {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"

  val dbURL = "jdbc:derby:myDB;create=true;";

  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  implicit val session = AutoSession

}
object Database extends Database{
  def setUpDB() = {
    if (!hasDBInitialize)
      Player.initializeTable()
  }
  def hasDBInitialize : Boolean = {

    DB getTable "Player" match {
      case Some(x) => true
      case None => false
    }

  }
}

