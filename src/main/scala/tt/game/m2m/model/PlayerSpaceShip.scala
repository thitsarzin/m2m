package tt.game.m2m.model

import scalafx.scene.image.Image

class PlayerSpaceShip(
                       name: String,
                       image: Image,
                       size: Double
                     ) extends SpaceShip(name, image, size) with Shootable {

  override def toString: String = name

  def shoot(): Unit = {
    // Implement shooting logic for the player spaceship
  }
}

object PlayerSpaceShip {
  val ss1 = new PlayerSpaceShip(
    "Nova Horizon",
    new Image(getClass.getResourceAsStream("/images/GreenSS.png")),
    50
  )

  val ss2 = new PlayerSpaceShip(
    "Stellar Phoenix",
    new Image(getClass.getResourceAsStream("/images/OrangeSS.png")),
    50
  )

  val ss3 = new PlayerSpaceShip(
    "Lunar Tempest",
    new Image(getClass.getResourceAsStream("/images/BlueSS.png")),
    50
  )

  val ss4 = new PlayerSpaceShip(
    "Nebula Voyager",
    new Image(getClass.getResourceAsStream("/images/Blue2SS.png")),
    50
  )

  val ss5 = new PlayerSpaceShip(
    "Galactic Sentinel",
    new Image(getClass.getResourceAsStream("/images/LPurpleSS.png")),
    50
  )

  val ss6 = new PlayerSpaceShip(
    "Cosmic Vortex",
    new Image(getClass.getResourceAsStream("/images/PinkSS.png")),
    50
  )

  val ss7 = new PlayerSpaceShip(
    "Astro Enforcer",
    new Image(getClass.getResourceAsStream("/images/RedSS.png")),
    50
  )

  val ss8 = new PlayerSpaceShip(
    "Quantum Wraith",
    new Image(getClass.getResourceAsStream("/images/WhiteSS.png")),
    50
  )

  val ss9 = new PlayerSpaceShip(
    "Celestial Striker",
    new Image(getClass.getResourceAsStream("/images/YellowSS.png")),
    50
  )

  val spaceShips: List[PlayerSpaceShip] = List(ss1, ss2, ss3, ss4, ss5, ss6, ss7, ss8, ss9)
}



