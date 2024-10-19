package tt.game.m2m.controller

import scalafx.scene.control.{Alert, ButtonType}
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml
import tt.game.m2m.MainApp

@sfxml
class RootLayoutController() {

  def handleExit(): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      initOwner(MainApp.stage)
      title = "Exit Confirmation"
      headerText = "Are you sure you want to exit?"
      contentText = "You will be exit the game completely. You can no longer perform another function."
    }
    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) => System.exit(0) // Exit the application
      case _ => // Do nothing, stay in the application
    }
  }
  def handleMainPage(): Unit = {
    val alert = new Alert(AlertType.Confirmation) {
      initOwner(MainApp.stage)
      title = "Confirmation"
      headerText = "Are you sure you want to go back?"
      contentText = "Once you click OK, you will go back to the main page and unsaved progress will be lost."
    }
    val result = alert.showAndWait()
    result match {
      case Some(ButtonType.OK) => MainApp.showHomePage() // Back to the main page
      case _ => // Do nothing,
    }
  }
}