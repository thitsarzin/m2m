package tt.game.m2m.model

case class GameLevel(
                      bossHealth: Int,
                      wordsToType: List[String]
                    ) {
  override def toString: String = this match {
    case GameLevel.Easy   => "Easy"
    case GameLevel.Normal => "Normal"
    case GameLevel.Hard   => "Hard"
    case _                => "Unknown"
  }
}

object GameLevel {
  val Easy = GameLevel(
    bossHealth = 5,
    wordsToType = List("moon", "comet", "planet", "galaxy", "Star", "Ship", "Rocket", "alien", "Space", "Laser", "Astro", "Orbit")
  )

  val Normal = GameLevel(
    bossHealth = 10,
    wordsToType = List("rocket", "asteroid", "nebula", "quasar", "meteor", "NeBula", "METEOR", "comet", "AstEROiD", "SoLaR", "VOYAGE", "plasma", "quAsAr", "OrbiTing", "cELestiAL")
  )

  val Hard = GameLevel(
    bossHealth = 15,
    wordsToType = List("supernova", "blackhole", "constellation", "interstellar", "multiverse", "QuASAr", "GRAvity", "Neutron", "sUPERNOVA", "eLecTrON", "HYPERSpace", "aSTRoid", "plaNetArY")
  )
}
