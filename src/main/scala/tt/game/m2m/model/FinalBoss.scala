package tt.game.m2m.model

import scalafx.scene.image.Image

class FinalBoss(
                 val health: Int,
                 val image: Image) extends Shootable {
  var currentHealth: Int = health

  override def shoot(): Unit = {
  }

  def takeDamage(): Unit = {
    currentHealth -= 1
  }

  def powerUp(): Unit = {
    currentHealth += 1
  }

  def isDefeated: Boolean = currentHealth <= 0
}

object FinalBoss {
  def apply(health: Int = 5, imagePath: String): FinalBoss = {
    val image = new Image(getClass.getResourceAsStream("/images/FinalBoss.png"))
    new FinalBoss(health, image)
  }
}