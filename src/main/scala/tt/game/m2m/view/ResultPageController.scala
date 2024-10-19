package tt.game.m2m.controller

import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import tt.game.m2m.MainApp
import tt.game.m2m.MainApp.Player_Data

@sfxml
class ResultPageController(
                           private val homeButton: Button,
                           private val resultImage: ImageView,
                           private val nameLabel: Label,
                           private val scoreLabel: Label,
                           private val levelLabel: Label
                         ) {

  resultImage.image = new Image(getClass.getResourceAsStream("/images/Result_bg.png"))

  nameLabel.text = Player_Data.playerName
  scoreLabel.text = Player_Data.elapsedTime
  levelLabel.text = Player_Data.selectedLevel

  homeButton.onAction = _ => {
    MainApp.showHomePage()
  }

}