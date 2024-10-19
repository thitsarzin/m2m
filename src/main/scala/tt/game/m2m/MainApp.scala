package tt.game.m2m

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.image.Image
import tt.game.m2m.model.{GameLevel, Player, PlayerSpaceShip}
import tt.game.m2m.util.Database


object MainApp extends JFXApp {

  Database.setUpDB()
  Player.playerData ++= Player.getAllPlayers

  // Variable to hold the selected player spaceship
  var selectedPlayerShip: PlayerSpaceShip = _
  var selectedLevel: GameLevel = _
  var playerName: String = _
  var elapsedTime: String = _

  object Player_Data {
    var playerName: String = _
    var elapsedTime: String = _
    var selectedLevel: String = _
  }

  // transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  // initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load();
  // retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // initialize stage
  stage = new PrimaryStage {
    title = "Mission 2 Moon"
    icons += new Image(getClass.getResourceAsStream("/images/M2M_logo.png"))
    scene = new Scene {
      root = roots
      stylesheets = Seq(getClass.getResource("view/Design.css").toString)
    }
  }
  // actions for Home Page window
  def showHomePage() = {
    val resource = getClass.getResource("view/HomePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showSelectionPage() = {
    val resource = getClass.getResource("view/SelectionPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showRecordPage() = {
    val resource = getClass.getResource("view/Record.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showRulesPage() = {
    val resource = getClass.getResource("view/RulesPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showEasyGamePage() = {
    selectedLevel = GameLevel.Easy
    val resource = getClass.getResource("view/GamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showNormalGamePage() = {
    selectedLevel = GameLevel.Normal
    val resource = getClass.getResource("view/GamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showHardGamePage() = {
    selectedLevel = GameLevel.Hard
    val resource = getClass.getResource("view/GamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showGamePage(): Unit = {
    // Load the game page based on the selectedLevel
    selectedLevel match {
      case GameLevel.Easy => showEasyGamePage()
      case GameLevel.Normal => showNormalGamePage()
      case GameLevel.Hard => showHardGamePage()
      case _ => println("Invalid level selected.")
    }
  }

  def endGame(): Unit = {
    val player = new Player(playerName, elapsedTime, selectedLevel.toString)
    player.save()
    Player.playerData += player
    Player_Data.playerName = playerName
    Player_Data.elapsedTime = elapsedTime
    Player_Data.selectedLevel = selectedLevel.toString
    showResultPage()
  }

  def showResultPage() = {
    val resource = getClass.getResource("view/ResultPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // call to display PersonOverview when app start
  showHomePage()
}
