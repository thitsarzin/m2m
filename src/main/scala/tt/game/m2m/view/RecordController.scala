package tt.game.m2m.controller

import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import tt.game.m2m.MainApp
import tt.game.m2m.model.Player

@sfxml
class RecordController(
                        private val playerTable: TableView[Player],
                        private val nameColumn: TableColumn[Player, String],
                        private val bestTimeColumn: TableColumn[Player, String],
                        private val modeColumn: TableColumn[Player, String],
                        private val homeButton: Button
                      ) {

  nameColumn.cellValueFactory = {_.value.name}
  bestTimeColumn.cellValueFactory = {_.value.scoreTime}
  modeColumn.cellValueFactory = {_.value.mode}

  playerTable.items = Player.playerData

  homeButton.onAction = _ => {
    MainApp.showHomePage()
  }

}

