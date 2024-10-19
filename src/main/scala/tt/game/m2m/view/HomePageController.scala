package tt.game.m2m.controller

import scalafx.scene.image.{Image, ImageView}
import tt.game.m2m.MainApp
import scalafxml.core.macros.sfxml

@sfxml
class HomePageController (
                           private val backgroundImage: ImageView
                         ) {

  backgroundImage.image = new Image(getClass.getResourceAsStream("/images/home_bg.png"))

  def handlePlay(): Unit = {
    MainApp.showSelectionPage()
  }

  def handleRank(): Unit = {
    MainApp.showRecordPage()
  }

  def handleRules(): Unit = {
    MainApp.showRulesPage()
  }
}
