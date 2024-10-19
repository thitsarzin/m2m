package tt.game.m2m.model

import tt.game.m2m.util.Database
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalikejdbc._

import scala.util.Try

class Player (_name: String, _scoreTime: String, _mode: String ){
  val name = new StringProperty(_name)
  val scoreTime = new StringProperty(_scoreTime)
  val mode = new StringProperty(_mode)

  def save() : Try[Int] =
    Try(DB autoCommit { implicit session =>
      sql"""
				insert into player (name, scoreTime, mode) values
				(${name.value}, ${scoreTime.value}, ${mode.value})
			""".update.apply()
    })
}

object Player extends Database{
  val playerData = new ObservableBuffer[Player]()
  def apply (
              _name : String,
              _scoreTime : String,
              _mode : String
            ) : Player = {
    new Player(_name, _scoreTime, _mode) {
      name.value = _name
      scoreTime.value     = _scoreTime
      mode.value = _mode
    }
  }

  def initializeTable() = {
    DB autoCommit { implicit session =>
      sql"""
			create table player (
			  id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
			  name varchar(64),
			  scoreTime varchar (10),
			  mode varchar(10)
			)
			""".execute.apply()
    }
  }

  def getAllPlayers : List[Player] = {
    DB readOnly { implicit session =>
      sql"select * from player".map(rs => Player(rs.string("name"),
        rs.string("scoreTime"),
        rs.string("mode") )).list.apply()
    }
  }
}

