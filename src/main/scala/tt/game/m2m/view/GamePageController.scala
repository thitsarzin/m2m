package tt.game.m2m.controller

import scalafx.Includes.observableList2ObservableBuffer
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.Platform
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.image.Image
import scalafx.scene.input.KeyEvent
import scalafx.util.Duration
import scala.jdk.CollectionConverters.asScalaBufferConverter
import scalafx.scene.layout.GridPane
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import java.util.{Timer, TimerTask}
import javafx.scene.image.ImageView
import tt.game.m2m.MainApp
import tt.game.m2m.model.{FinalBoss, GameLevel}
import tt.game.m2m.util.CommonUtil

@sfxml
class GamePageController(
                          private val gameGridPane: GridPane,
                          private val timeLabel: Label,
                          private val bossLives: Label,
                          private val bossLivesLabel: Label,
                          private val wordToType: Label,
                          private val wordToTypeLabel: Label,
                          private val wordToTypeImage: ImageView
                        ) {
  private val playerBulletImage = new Image(getClass.getResourceAsStream("/images/PlayerBullet.png"))
  val enemyImage = new Image(getClass.getResourceAsStream("/images/EnemySS.png"))
  val playerImage = MainApp.selectedPlayerShip.image
  val matchImage = new Image(getClass.getResourceAsStream("/images/Damage.png"))
  val noMatchImage = new Image(getClass.getResourceAsStream("/images/Miss.png"))

  private var playerImageView: ImageView = _
  private var playerColumnIndex: Int = 5 // Default starting position, middle of the grid
  private val playerRowIndex: Int = 7 // Fixed row for player spaceship
  var finalBoss: FinalBoss = _
  var finalBossImageView: ImageView = _

  private var gameTimer: Timer = _
  private var startTime: Long = _
  private var timerTimeline: Timeline = _
  private var elapsedTime: Long = 0L
  wordToTypeImage.image = new Image(getClass.getResourceAsStream("/images/wordToType_bg.png"))

  private var currentGameLevel: GameLevel = _

  initialize()

  def initialize(): Unit = {
    currentGameLevel = MainApp.selectedLevel
    placeSpaceShips()
    setupKeyHandling()
    startTimer()
    bossLives.visible = false
    bossLivesLabel.visible = false
    wordToType.visible = false
    wordToTypeLabel.visible = false
    wordToTypeImage.visible = false
  }

  def startTimer(): Unit = {
    startTime = System.currentTimeMillis()
    gameTimer = new Timer()
    gameTimer.scheduleAtFixedRate(new TimerTask {
      override def run(): Unit = {
        elapsedTime = System.currentTimeMillis() - startTime
      }
    }, 0, 100) // Start immediately and repeat every 100 milliseconds

    // Update the time label every second
    timerTimeline = new Timeline {
      keyFrames = Seq(
        KeyFrame(Duration(1000), onFinished = _ => updateTimeLabel()) // 1000 milliseconds = 1 second
      )
      cycleCount = Timeline.Indefinite
      play() // Start the timeline
    }
  }

  def updateTimeLabel(): Unit = {
    val currentDuration = Duration(elapsedTime)
    timeLabel.text = CommonUtil.durationToTimerString(currentDuration)
  }

  def stopTimer(): Unit = {
    gameTimer.cancel()
    timerTimeline.stop()
    val formattedTime = CommonUtil.durationToTimerString(Duration(elapsedTime))
    MainApp.elapsedTime = formattedTime
  }

  def placeSpaceShips(): Unit = {

    for (node <- gameGridPane.getChildren) {
      if (node.isInstanceOf[ImageView]) {
        val imageView = node.asInstanceOf[ImageView]
        // Get the position of the ImageView
        val columnIndex = GridPane.getColumnIndex(imageView)
        val rowIndex = GridPane.getRowIndex(imageView)

        if (columnIndex == 5 && rowIndex == 7) { // Check for specific position and set image for player
          imageView.image = playerImage
          playerImageView = imageView // Store reference to player spaceship's ImageView
        } else {
          imageView.image = enemyImage
        }
      } else {
        println("Node is not an ImageView")
      }
    }
  }

  def setupKeyHandling(): Unit = {
    Platform.runLater {
      gameGridPane.scene().onKeyPressed = (event: KeyEvent) => {
        event.code match {
          case scalafx.scene.input.KeyCode.Left => movePlayerLeft()
          case scalafx.scene.input.KeyCode.Right => movePlayerRight()
          case scalafx.scene.input.KeyCode.Space => shootBullet()
          case _ => // Do nothing for other keys
        }
      }
    }
  }

  def movePlayerLeft(): Unit = {
    if (playerColumnIndex > 0) { // Ensure we don't move off the grid
      playerColumnIndex -= 1
      GridPane.setColumnIndex(playerImageView, playerColumnIndex)
    }
  }

  def movePlayerRight(): Unit = {
    val maxColumns = gameGridPane.getColumnConstraints.size() // Use the number of defined columns
    if (playerColumnIndex < maxColumns - 1) { // Ensure we don't move off the grid
      playerColumnIndex += 1
      GridPane.setColumnIndex(playerImageView, playerColumnIndex)
    }
  }

  def shootBullet(): Unit = {
    val bulletImageView = new ImageView(playerBulletImage)

    bulletImageView.fitWidth = 50
    bulletImageView.fitHeight = 50

    GridPane.setColumnIndex(bulletImageView, playerColumnIndex)
    GridPane.setRowIndex(bulletImageView, playerRowIndex - 1) // Start right above the player
    gameGridPane.getChildren.add(bulletImageView) // Add bullet to the grid

    val timeline = new Timeline { // Animate bullet movement upwards
      keyFrames = Seq(
        KeyFrame(Duration(100), onFinished = _ => moveBulletUp(bulletImageView))
      )
      cycleCount = Timeline.Indefinite
    }
    timeline.play()
  }

  def checkCollision(bullet: ImageView): Option[ImageView] = {
    val bulletRowIndex = Option(GridPane.getRowIndex(bullet)).getOrElse(0)
    val bulletColumnIndex = Option(GridPane.getColumnIndex(bullet)).getOrElse(0)

    for (node <- gameGridPane.getChildren.asScala) {
      if (node.isInstanceOf[ImageView] && node != bullet && node != playerImageView) {
        val imageView = node.asInstanceOf[ImageView]
        val enemyRowIndex = Option(GridPane.getRowIndex(imageView)).getOrElse(0)
        val enemyColumnIndex = Option(GridPane.getColumnIndex(imageView)).getOrElse(0)

        if (enemyRowIndex == bulletRowIndex && enemyColumnIndex == bulletColumnIndex) {
          return Some(imageView)
        }
      }
    }
    None
  }

  def moveBulletUp(bullet: ImageView): Unit = {
    val currentRowIndex = GridPane.getRowIndex(bullet)

    if (currentRowIndex == null || currentRowIndex <= 0) { // Bullet is off the grid
      gameGridPane.getChildren.remove(bullet)
    } else { // Move bullet up by one row
      val newRowIndex = currentRowIndex - 1
      GridPane.setRowIndex(bullet, newRowIndex)

      // Check for collision with an enemy
      checkCollision(bullet) match {
        case Some(enemy) =>
          gameGridPane.getChildren.remove(enemy) // Remove enemy
          gameGridPane.getChildren.remove(bullet) // Remove bullet

          if (checkAllEnemiesDefeated) {
            allEnemiesDefeated() // Trigger final boss if all enemies are defeated
          }
        case None => // No collision detected
      }
    }
  }

  def checkAllEnemiesDefeated: Boolean = { // Check if there are any enemy ImageViews left on the grid
    for (node <- gameGridPane.getChildren.asScala) {
      if (node.isInstanceOf[ImageView] && node != playerImageView) {
        val imageView = node.asInstanceOf[ImageView]
        val columnIndex = GridPane.getColumnIndex(imageView)
        val rowIndex = GridPane.getRowIndex(imageView)

        if (rowIndex < playerRowIndex && rowIndex >= 0 && columnIndex >= 0) {
          return false
        }
      }
    }
    true
  }

  def allEnemiesDefeated(): Unit = {
    repositionPlayerShip() // Move player ship to original position
    showFinalBoss()
    startTypingGame()
  }

  def repositionPlayerShip(): Unit = {
    GridPane.setRowIndex(playerImageView, 7)
    GridPane.setColumnIndex(playerImageView, 5)
  }

  def displayTemporaryImage(image: Image, column: Int, row: Int, durationMillis: Int): Unit = {
    val imageView = new ImageView(image)
    imageView.fitWidth = 100  // Set the desired width
    imageView.fitHeight = 100 // Set the desired height
    GridPane.setColumnIndex(imageView, column)
    GridPane.setRowIndex(imageView, row)
    gameGridPane.getChildren.add(imageView)

    // Timeline to remove the image after the specified duration
    val timeline = new Timeline {
      keyFrames = Seq(
        KeyFrame(Duration(durationMillis), onFinished = _ => gameGridPane.getChildren.remove(imageView))
      )
    }
    timeline.play()
  }

  def showFinalBoss(): Unit = {
    finalBoss = FinalBoss(imagePath = "/images/FinalBoss.png", health = currentGameLevel.bossHealth)
    finalBossImageView = new ImageView(finalBoss.image)
    finalBossImageView.setFitWidth(400)
    finalBossImageView.setFitHeight(300)

    GridPane.setRowIndex(finalBossImageView, 3)
    GridPane.setColumnIndex(finalBossImageView, 2)
    gameGridPane.add(finalBossImageView, 2, 3)

    bossLives.visible = true
    bossLivesLabel.visible = true
    wordToType.visible = true
    wordToTypeLabel.visible = true
    wordToTypeImage.visible = true
  }


  // Typing game mechanism
  def startTypingGame(): Unit = {
    val wordsToType = currentGameLevel.wordsToType

    val playerInput = new TextField()
    gameGridPane.add(playerInput, 5, 6) // Positioned above the player ship

    def updateWordLabel(): Unit = {
      val randomWord = wordsToType(scala.util.Random.nextInt(wordsToType.length))
      wordToType.text = randomWord

    }
    def updateBossLivesLabel(): Unit = {
      bossLivesLabel.text = s"${finalBoss.currentHealth}"
    }

    updateWordLabel()
    updateBossLivesLabel()

    playerInput.onAction = _ => {
      if (playerInput.text.value == wordToType.text.value) {
        displayTemporaryImage(matchImage, 6, 4, 1000)
        finalBoss.takeDamage()
        updateBossLivesLabel()
        if (finalBoss.isDefeated) {
          gameGridPane.getChildren.remove(finalBossImageView)
          stopTimer()
          MainApp.endGame()
        } else {
          updateWordLabel() // Update to a new random word
        }
      } else {
        finalBoss.powerUp()
        updateBossLivesLabel()
        displayTemporaryImage(noMatchImage, 8, 2, 1000)
      }
      playerInput.clear()
    }
  }

}
