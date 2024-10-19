package tt.game.m2m.model

import scalafx.scene.image.Image

class EnemySpaceShip(
                      name: String,
                      image: Image,
                      size: Double,
                    ) extends SpaceShip(name, image, size) with Shootable {

  def shoot(): Unit = {

  }
}



