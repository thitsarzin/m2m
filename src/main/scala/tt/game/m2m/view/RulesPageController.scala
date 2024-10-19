package tt.game.m2m.controller

import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import tt.game.m2m.MainApp

@sfxml
class RulesPageController(
                        private val homeButton: Button,
                        private val rulesImage: ImageView
                      ) {

  rulesImage.image = new Image(getClass.getResourceAsStream("/images/Rule.png"))
  homeButton.onAction = _ => {
    MainApp.showHomePage()
  }
}