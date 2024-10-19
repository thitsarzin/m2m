package tt.game.m2m.controller

import scalafx.scene.control.{Alert, Button, Label, ListView, TextField}
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml
import tt.game.m2m.MainApp
import tt.game.m2m.model.{GameLevel, Player, PlayerSpaceShip}
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.Alert.AlertType
import tt.game.m2m.MainApp.selectedLevel

@sfxml
class SelectionPageController(
                               private val playerNameField: TextField,
                               private val spaceShipList: ListView[PlayerSpaceShip],
                               private val spaceShipImage: ImageView,
                               private val selectedLevelLabel: Label,
                               private val easyButton: Button,
                               private val normalButton: Button,
                               private val hardButton: Button,
                               private val homeButton: Button,
                               private val startButton: Button

                             ) {

  spaceShipList.items = ObservableBuffer(PlayerSpaceShip.spaceShips: _*)

  // Set the initial selected spaceship
  spaceShipList.selectionModel().selectedItem.onChange { (_, _, selectedShip) =>
    selectSpaceShip(selectedShip)
    MainApp.selectedPlayerShip = selectedShip // Store the selected spaceship in MainApp
  }

  // Automatically select the first spaceship in the list
  spaceShipList.selectionModel().selectFirst()

  // Define actions for level buttons
  easyButton.onAction = _ => selectLevel(GameLevel.Easy)
  normalButton.onAction = _ => selectLevel(GameLevel.Normal)
  hardButton.onAction = _ => selectLevel(GameLevel.Hard)

  homeButton.onAction = _ => {
    MainApp.showHomePage()
  }

  startButton.onAction = _ => {
    if (validateInputs()) {
      MainApp.playerName = playerNameField.text.value
      MainApp.showGamePage()
    }
  }

  private def validateInputs(): Boolean = {
    if (playerNameField.text.value.trim.isEmpty || selectedLevel == null) {
      val alert = new Alert(AlertType.Warning) {
        initOwner(MainApp.stage)
        title = "No Name or Level"
        headerText = "No Name or Level could not identify"
        contentText = "Please fill in the name or select the level of your preference."
      } showAndWait()
      false
    } else {
      true
    }
  }

// Method to update UI when a spaceship is selected
  def selectSpaceShip(ship: PlayerSpaceShip): Unit = {
    spaceShipImage.image = ship.image
  }

  // Method to select a game level
  private def selectLevel(level: GameLevel): Unit = {
    MainApp.selectedLevel = level
    selectedLevelLabel.text = level.toString
  }

}
